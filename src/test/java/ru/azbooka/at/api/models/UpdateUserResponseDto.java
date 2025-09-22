package ru.azbooka.at.api.models;

import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.Map;

import static lombok.AccessLevel.PRIVATE;

@Data
@FieldDefaults(level = PRIVATE)
public class UpdateUserResponseDto {
    UserResponseDto user;
    Map<String, Object> messages;
}
