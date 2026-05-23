package com.diogenes.service;

import com.diogenes.model.AppUser;
import com.diogenes.model.RegisterForm;
import com.diogenes.repository.AppUserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserAccountService {

    private final AppUserRepository appUserRepository;
    private final PasswordEncoder passwordEncoder;

    public UserAccountService(AppUserRepository appUserRepository, PasswordEncoder passwordEncoder) {
        this.appUserRepository = appUserRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public AppUser register(RegisterForm form) {
        if (!form.getPassword().equals(form.getConfirmPassword())) {
            throw new IllegalArgumentException("Passwords do not match.");
        }
        if (appUserRepository.existsByEmailIgnoreCase(form.getEmail())) {
            throw new IllegalArgumentException("An account with this email already exists.");
        }

        AppUser user = new AppUser();
        user.setEmail(form.getEmail().trim().toLowerCase());
        user.setDisplayName(form.getDisplayName().trim());
        user.setPasswordHash(passwordEncoder.encode(form.getPassword()));
        return appUserRepository.save(user);
    }

    public boolean passwordMatches(String rawPassword, String passwordHash) {
        return passwordEncoder.matches(rawPassword, passwordHash);
    }
}
