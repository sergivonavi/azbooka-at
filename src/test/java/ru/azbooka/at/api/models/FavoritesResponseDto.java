package ru.azbooka.at.api.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.List;

import static com.fasterxml.jackson.annotation.JsonCreator.Mode.DELEGATING;
import static lombok.AccessLevel.PRIVATE;

@Data
@NoArgsConstructor
@FieldDefaults(level = PRIVATE)
public class FavoritesResponseDto {
    List<String> codes;

    @JsonCreator(mode = DELEGATING)
    public FavoritesResponseDto(List<String> codes) {
        this.codes = codes;
    }

    @JsonValue
    public List<String> toJson() {
        return codes;
    }
}
