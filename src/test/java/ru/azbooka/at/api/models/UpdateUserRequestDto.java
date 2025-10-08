package ru.azbooka.at.api.models;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import static lombok.AccessLevel.PRIVATE;

@Data
@NoArgsConstructor
@FieldDefaults(level = PRIVATE)
public class UpdateUserRequestDto {
    String firstName;
    String lastName;
    String email;
    String avatar;
}
