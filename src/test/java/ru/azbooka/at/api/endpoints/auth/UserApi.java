package ru.azbooka.at.api.endpoints.auth;

import io.qameta.allure.Step;
import io.restassured.specification.RequestSpecification;
import ru.azbooka.at.api.models.UpdateUserRequestDto;
import ru.azbooka.at.api.models.UpdateUserResponseDto;
import ru.azbooka.at.api.models.UserResponseDto;
import ru.azbooka.at.utils.FileUtils;

import java.io.File;

import static io.restassured.RestAssured.given;
import static ru.azbooka.at.api.specs.BaseSpecs.requestSpec;
import static ru.azbooka.at.api.specs.BaseSpecs.responseSpec;

public class UserApi {

    @Step("Получаем информацию о текущем пользователе")
    public static UserResponseDto getUser(String token) {
        return requestSpec(token)
                .when()
                .get("/auth/user")
                .then()
                .spec(responseSpec(200))
                .extract().as(UserResponseDto.class);
    }

    @Step("Обновляем информацию о текущем пользователе")
    public static UpdateUserResponseDto updateUser(String token, UpdateUserRequestDto dto) {
        RequestSpecification spec = given()
                .auth().oauth2(token)
                .multiPart("first_name", dto.getFirstName(), "text/plain; charset=UTF-8")
                .multiPart("last_name", dto.getLastName(), "text/plain; charset=UTF-8")
                .multiPart("email", dto.getEmail());

        if (dto.getAvatar() != null) {
            File avatar = FileUtils.getFile(dto.getAvatar());
            spec.multiPart("avatar[]", avatar, FileUtils.getContentType(avatar));
        }

        return spec
                .when()
                .post("/auth/user")
                .then()
                .spec(responseSpec(200))
                .extract().as(UpdateUserResponseDto.class);
    }
}
