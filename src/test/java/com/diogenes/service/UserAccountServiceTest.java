package com.diogenes.service;

import com.diogenes.model.AppUser;
import com.diogenes.model.RegisterForm;
import com.diogenes.repository.AppUserRepository;
import com.diogenes.security.DiogenesUserDetailsService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DataJpaTest
@Import({UserAccountService.class, DiogenesUserDetailsService.class, BCryptPasswordEncoder.class})
class UserAccountServiceTest {

    @Autowired
    private UserAccountService userAccountService;

    @Autowired
    private AppUserRepository appUserRepository;

    @Autowired
    private DiogenesUserDetailsService userDetailsService;

    @Test
    void register_storesBcryptHashAndCanAuthenticate() {
        RegisterForm form = new RegisterForm();
        form.setEmail("Alex@Example.com");
        form.setDisplayName("Alex");
        form.setPassword("password123");
        form.setConfirmPassword("password123");

        AppUser saved = userAccountService.register(form);

        assertThat(saved.getId()).isNotNull();
        assertThat(saved.getEmail()).isEqualTo("alex@example.com");
        assertThat(saved.getPasswordHash()).startsWith("$2");
        assertThat(saved.getPasswordHash()).isNotEqualTo("password123");
        assertThat(userAccountService.passwordMatches("password123", saved.getPasswordHash())).isTrue();

        var principal = userDetailsService.loadUserByUsername("alex@example.com");
        assertThat(principal.getUsername()).isEqualTo("alex@example.com");
        assertThat(appUserRepository.count()).isEqualTo(1);
    }

    @Test
    void register_rejectsDuplicateEmail() {
        RegisterForm form = new RegisterForm();
        form.setEmail("user@example.com");
        form.setDisplayName("User");
        form.setPassword("password123");
        form.setConfirmPassword("password123");
        userAccountService.register(form);

        assertThatThrownBy(() -> userAccountService.register(form))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("already exists");
    }

    @Test
    void loadUserByUsername_failsForUnknownEmail() {
        assertThatThrownBy(() -> userDetailsService.loadUserByUsername("missing@example.com"))
                .isInstanceOf(UsernameNotFoundException.class);
    }
}
