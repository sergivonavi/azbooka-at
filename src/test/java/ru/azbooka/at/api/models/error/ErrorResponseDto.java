package ru.azbooka.at.api.models.error;

import lombok.Data;
import lombok.experimental.FieldDefaults;

import static lombok.AccessLevel.PRIVATE;

@Data
@FieldDefaults(level = PRIVATE)
public class ErrorResponseDto {
    String detail;
}
