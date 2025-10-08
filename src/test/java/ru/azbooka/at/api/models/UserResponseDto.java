package ru.azbooka.at.api.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import static lombok.AccessLevel.PRIVATE;

@Data
@FieldDefaults(level = PRIVATE)
public class UserResponseDto {
    String avatar;
    Integer id;
    String username;
    @JsonProperty("first_name")
    String firstName;
    @JsonProperty("last_name")
    String lastName;
    String email;
    @JsonProperty("last_login")
    String lastLogin;
    @JsonProperty("date_joined")
    String dateJoined;
    @JsonProperty("play_trailer")
    Boolean playTrailer;
}
