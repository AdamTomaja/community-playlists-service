package com.cydercode.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.hibernate.validator.constraints.URL;

@Data
public class CreateMusicItemRequest {

  @NotEmpty private String userID;

  @Size(max = 255)
  @NotEmpty private String username;

  @Size(max = 255)
  @NotEmpty @URL private String link;

  @Size(max = 255)
  private String description;
}
