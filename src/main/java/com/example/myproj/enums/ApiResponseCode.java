package com.example.myproj.enums;

import java.io.Serializable;

/**
 * ApiResponseCode.
 *
 * @author Evgeny_Ageev
 */
public enum ApiResponseCode implements Serializable {
    BAD_CREDENTIALS(1, "Неверное имя пользователя или пароль"),
    TOKEN_EXPIRED(2, "Время жизни токена окончено"),
    INVALID_TOKEN(3, "Невалидный токен"),
    INVALID_TOKEN_SIGNATURE(4, "Невалидная подпись токена"),
    INVALID_TOKEN_DATA(5, "Невалидные данные в токене"),
    ACCESS_DENIED(6, "Доступ запрещен"),
    INTERNAL_SERVER_ERROR(7, "Неизвестная ошибка сервера"),
    NOT_FOUND(8, "Запрашиваемый ресурс не найден"),
    METHOD_NOT_ALLOWED(9, "неверный HTTP метод"),
    UNSUPPORTED_MEDIA_TYPE(10, "Неподдерживаемый тип контента"),
    NOT_ACCEPTABLE(11, "Неприемлемый тип ответа"),
    BAD_REQUEST(12, "Невалидный HTTP запрос"),
    SERVICE_UNAVAILABLE(13, "Сервис недоступен"),
    USER_EXISTS(14, "Пользователь с таким именем существует"),
    INCORRECT_PHONE_NUMBER(15, "Некорректный формат номера телефона. Номер должен начинаться с цифры 7 и содержать 11 цифр без каких-либо символов"),
    FAILED_TO_CREATE_USER(16, "Ошибка при создании пользователя"),
    FAILED_TO_UPDATE_USER_PASSWORD(17, "Ошибка при смене пароля пользователя"),
    PHONE_NUMBER_NOT_FOUND(18, "Номер телефона в системе не найден"),
    USER_NOT_EXISTS(19, "Пользователь с указанным id не существует");


    private final int code;
    private final String description;

    ApiResponseCode(int code, String description) {
        this.code = code;
        this.description = description;
    }

    public int getCode() {
        return code;
    }

    public String getDescription(Object... params) {
        if (params == null || params.length == 0) {
            return description;
        } else {
            return String.format(description, params);
        }
    }
}
