package ru.azbooka.at.api.models.error;

import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.List;

import static lombok.AccessLevel.PRIVATE;

@Data
@FieldDefaults(level = PRIVATE)
public class ValidationErrorResponseDto {
    List<Detail> detail;

    @Data
    @FieldDefaults(level = PRIVATE)
    public static class Detail {
        String type;
        List<String> loc;
        String msg;
    }
}
