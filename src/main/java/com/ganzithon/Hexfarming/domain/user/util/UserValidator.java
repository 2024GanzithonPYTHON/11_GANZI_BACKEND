package com.ganzithon.Hexfarming.domain.user.util;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class UserValidator {
    private final static int MIN_PASSWORD_LENGTH = 8;
    private final static int MAX_PASSWORD_LENGTH = 16;

    public static void validatePassword(String password, String rePassword) {
        validateRePasswordIsCorrect(password, rePassword);
        validatePasswordLength(password);
    }

    private static void validateRePasswordIsCorrect(String password, String rePassword) throws IllegalArgumentException {
        if (!password.equals(rePassword)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "비밀번호가 일치하지 않습니다.");
        }
    }

    private static void validatePasswordLength(String password) {
        if (password.length() < MIN_PASSWORD_LENGTH || password.length() > MAX_PASSWORD_LENGTH) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "비밀번호는 8자 이상, 16자 이하여야 합니다.");
        }
    }
}
