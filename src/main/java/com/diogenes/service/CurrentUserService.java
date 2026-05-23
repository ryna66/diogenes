package com.diogenes.service;

import com.diogenes.model.AppUser;
import com.diogenes.security.UserPrincipal;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CurrentUserService {

    public Optional<AppUser> findCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return Optional.empty();
        }

        Object principal = authentication.getPrincipal();
        if (principal instanceof UserPrincipal userPrincipal) {
            return Optional.of(userPrincipal.getAppUser());
        }

        return Optional.empty();
    }

    public AppUser requireCurrentUser() {
        return findCurrentUser()
                .orElseThrow(() -> new IllegalStateException("No authenticated user in the current session."));
    }
}
