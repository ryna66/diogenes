package com.diogenes;

import com.diogenes.support.AuthenticatedMvcTestSupport;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@SpringBootTest
@AutoConfigureMockMvc
class ApplicationPageSmokeTest extends AuthenticatedMvcTestSupport {

    @Autowired
    private MockMvc mockMvc;

    private MockHttpSession session;

    @BeforeEach
    void loginBeforeEachPageCheck() throws Exception {
        session = new MockHttpSession();
        mockMvc.perform(post("/login")
                        .with(csrf())
                        .session(session)
                        .param("email", SMOKE_USER_EMAIL)
                        .param("password", "password123"))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    void mainPagesRenderWithDefaultProfile() throws Exception {
        mockMvc.perform(get("/").session(session))
                .andExpect(status().isOk())
                .andExpect(view().name("index"));

        mockMvc.perform(get("/assessment").session(session))
                .andExpect(status().isOk())
                .andExpect(view().name("assessment"));

        mockMvc.perform(get("/survey").session(session))
                .andExpect(status().isOk())
                .andExpect(view().name("survey"));

        mockMvc.perform(get("/journal").session(session))
                .andExpect(status().isOk())
                .andExpect(view().name("journal"));

        mockMvc.perform(get("/support").session(session))
                .andExpect(status().isOk())
                .andExpect(view().name("support"));

        mockMvc.perform(get("/about").session(session))
                .andExpect(status().isOk())
                .andExpect(view().name("about"));
    }
}
