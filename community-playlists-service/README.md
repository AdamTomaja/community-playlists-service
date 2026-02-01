# Community Playlists Service

Serwis backendowy do zarządzania elementami muzycznymi (np. utworami/albumami/playlistami – zależnie od typu) w kontekście „community playlists”. Aplikacja udostępnia REST API do tworzenia oraz wyszukiwania/listowania elementów muzycznych, mapuje DTO ↔ encje i zapisuje dane w bazie przy użyciu JPA.

---

## Funkcje

- Tworzenie elementu muzycznego (request/response DTO).
- Listowanie / wyszukiwanie elementów muzycznych (query service).
- Rozpoznawanie typu elementu (`ItemTypeResolverService`).
- Rozwiązywanie/zunifikowanie identyfikatorów zewnętrznych (`ExternalIDResolverService`).
- Mapowanie warstwy API do modelu domenowego (`MusicItemMapper`).
- Persistencja przez Spring Data JPA (`MusicItemsRepository`).
- Migracje bazy danych (Flyway) – `src/main/resources/db.migration/V1__init.sql`.

---

## Stos technologiczny

- **Java 21**
- **Gradle**
- **Spring MVC** (REST)
- **Spring Data JPA**
- **Flyway** (migracje DB)
- **Lombok**
- Testy: `src/test/groovy` (testy w Groovy)

---

## Struktura modułów (skrót)

- `com.cydercode.controller` – REST kontrolery (`MusicItemsController`)
- `com.cydercode.dto` – DTO dla API (`CreateMusicItemRequest/Response`, `ListMusicItemsResponse`)
- `com.cydercode.model` – model domenowy (`MusicItem`, `MusicItemType`)
- `com.cydercode.repository` – repozytoria JPA (`MusicItemsRepository`)
- `com.cydercode.service` – logika biznesowa i query (`MusicItemsService`, `QueryMusicItemsService`, resolvery)
- `src/main/resources/db.migration` – migracje Flyway
- `docker-compose.yml` – uruchomienie zależności (np. baza)

---

## Wymagania

- JDK **21**
- Docker + Docker Compose (opcjonalnie, ale zalecane dla bazy)
- (opcjonalnie) IntelliJ IDEA

---

## Konfiguracja

Konfiguracja aplikacji znajduje się w:

- `src/main/resources/application.yml`

Typowo znajdziesz tam ustawienia:
- połączenia z bazą danych (URL, użytkownik, hasło),
- port serwera,
- konfigurację Flyway/JPA.

Jeśli używasz Dockera do bazy, upewnij się, że wartości w `application.yml` odpowiadają temu, co wystawia `docker-compose.yml`.

---