package com.diogenes.config;

public final class SecurityPaths {

    public static final String[] PUBLIC = {
            "/login",
            "/register",
            "/css/**",
            "/h2-console/**"
    };

    private SecurityPaths() {
    }
}
