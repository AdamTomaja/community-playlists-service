package com.cydercode.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import org.hibernate.validator.constraints.URL;

@Data
public class CreateMusicItemRequest {

  @NotEmpty private String userID;

  @NotEmpty @URL private String link;
}
