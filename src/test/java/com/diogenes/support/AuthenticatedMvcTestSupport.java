package com.diogenes.support;

import com.diogenes.model.RegisterForm;
import com.diogenes.service.UserAccountService;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class AuthenticatedMvcTestSupport {

    public static final String SMOKE_USER_EMAIL = "smoke@example.com";

    @Autowired
    private UserAccountService userAccountService;

    @BeforeEach
    void ensureSmokeUserExists() {
        if (userAccountService == null) {
            return;
        }
        try {
            RegisterForm form = new RegisterForm();
            form.setEmail(SMOKE_USER_EMAIL);
            form.setDisplayName("Smoke Tester");
            form.setPassword("password123");
            form.setConfirmPassword("password123");
            userAccountService.register(form);
        } catch (IllegalArgumentException ignored) {
            // Account already exists for this test run.
        }
    }
}
