# syntax=docker/dockerfile:1.7

############################
# 1) Etap: build (Gradle)
############################
FROM eclipse-temurin:21-jdk AS build
WORKDIR /workspace

# Najpierw pliki buildowe – lepsze cache warstw Dockera
COPY gradlew settings.gradle build.gradle ./
COPY gradle ./gradle

# Podgrzej cache zależności (działa dobrze przy kolejnych buildach)
RUN --mount=type=cache,target=/root/.gradle \
    ./gradlew --no-daemon -v

# Dopiero potem źródła
COPY src ./src

# Zbuduj JAR (zakładam Spring Boot + Gradle)
RUN --mount=type=cache,target=/root/.gradle \
    ./gradlew --no-daemon clean bootJar

############################
# 2) Etap: runtime (lekki)
############################
FROM eclipse-temurin:21-jre AS runtime
WORKDIR /app

# Bezpieczniej: użytkownik bez uprawnień roota
RUN useradd -r -u 10001 -g root appuser

# (Opcjonalnie) ustaw domyślny port, dopasuj jeśli masz inny
EXPOSE 8080

# Skopiuj tylko artefakt z build stage
# Zakładam standard: build/libs/*.jar
COPY --from=build /workspace/build/libs/*.jar /app/app.jar

# Sensowne domyślne parametry JVM (możesz nadpisać w docker-compose)
ENV JAVA_OPTS="-XX:MaxRAMPercentage=75 -XX:+UseZGC -Dfile.encoding=UTF-8"
ENV SPRING_PROFILES_ACTIVE="docker"

USER 10001

# Prosty healthcheck (wymaga żeby aplikacja miała endpoint /actuator/health)
# Jeśli nie masz Spring Actuator, usuń ten blok albo zmień ścieżkę.
HEALTHCHECK --interval=30s --timeout=3s --start-period=30s --retries=3 \
  CMD wget -qO- http://127.0.0.1:8080/actuator/health | grep -q '"status"' || exit 1

ENTRYPOINT ["sh","-c","exec java $JAVA_OPTS -jar /app/app.jar"]