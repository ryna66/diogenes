package com.diogenes.auth;

import java.util.Arrays;
import java.util.Optional;

public enum LoginErrorCode {
    PASSWORD("password", "The password you entered is incorrect. Please try again."),
    EMAIL("email", "We could not find an account with that email. Create one below if you are new here."),
    GENERAL("general", "Sign in failed. Please check your email and password.");

    private final String paramValue;
    private final String message;

    LoginErrorCode(String paramValue, String message) {
        this.paramValue = paramValue;
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public static Optional<LoginErrorCode> fromParam(String errorCode) {
        if (errorCode == null || errorCode.isBlank()) {
            return Optional.empty();
        }
        return Arrays.stream(values())
                .filter(code -> errorCode.equals(code.paramValue))
                .findFirst()
                .or(() -> Optional.of(GENERAL));
    }
}
