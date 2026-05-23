package com.diogenes.security;

import com.diogenes.auth.LoginErrorCode;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class LoginAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {

    public LoginAuthenticationFailureHandler() {
        setAllowSessionCreation(true);
    }

    @Override
    public void onAuthenticationFailure(HttpServletRequest request,
                                        HttpServletResponse response,
                                        AuthenticationException exception) throws IOException, ServletException {
        LoginErrorCode errorCode = switch (exception) {
            case UsernameNotFoundException usernameNotFound -> LoginErrorCode.EMAIL;
            case BadCredentialsException badCredentials -> LoginErrorCode.PASSWORD;
            default -> LoginErrorCode.GENERAL;
        };

        setDefaultFailureUrl("/login?error=" + errorCode.name().toLowerCase());
        super.onAuthenticationFailure(request, response, exception);
    }
}
