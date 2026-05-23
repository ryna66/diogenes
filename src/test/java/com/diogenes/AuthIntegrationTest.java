package com.diogenes;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.unauthenticated;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrlPattern;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@SpringBootTest
@AutoConfigureMockMvc
class AuthIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void protectedPagesRedirectToLoginWhenAnonymous() throws Exception {
        mockMvc.perform(get("/journal"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrlPattern("**/login"));
    }

    @Test
    void registerLoginAndAccessProtectedPage() throws Exception {
        mockMvc.perform(get("/register"))
                .andExpect(status().isOk())
                .andExpect(view().name("register"));

        mockMvc.perform(post("/register")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("email", "step3-user@example.com")
                        .param("displayName", "Step Three")
                        .param("password", "password123")
                        .param("confirmPassword", "password123"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/login?registered"));

        MockHttpSession session = new MockHttpSession();
        mockMvc.perform(post("/login")
                        .with(csrf())
                        .session(session)
                        .param("email", "step3-user@example.com")
                        .param("password", "password123"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"));

        mockMvc.perform(get("/journal").session(session))
                .andExpect(status().isOk())
                .andExpect(view().name("journal"));
    }

    @Test
    void wrongPasswordShowsSpecificErrorMessage() throws Exception {
        mockMvc.perform(post("/register")
                        .with(csrf())
                        .param("email", "wrong-pass@example.com")
                        .param("displayName", "Wrong Pass")
                        .param("password", "password123")
                        .param("confirmPassword", "password123"))
                .andExpect(status().is3xxRedirection());

        mockMvc.perform(post("/login")
                        .with(csrf())
                        .param("email", "wrong-pass@example.com")
                        .param("password", "not-the-right-password"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/login?error=password"));

        mockMvc.perform(get("/login?error=password"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("The password you entered is incorrect")));
    }

    @Test
    void unknownEmailShowsSpecificErrorMessage() throws Exception {
        mockMvc.perform(post("/login")
                        .with(csrf())
                        .param("email", "nobody@example.com")
                        .param("password", "password123"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/login?error=email"));

        mockMvc.perform(get("/login?error=email"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("We could not find an account with that email")));
    }

    @Test
    void loginPageIsPublic() throws Exception {
        mockMvc.perform(get("/login"))
                .andExpect(status().isOk())
                .andExpect(view().name("login"))
                .andExpect(unauthenticated());
    }
}
