package com.diogenes;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@SpringBootTest
@AutoConfigureMockMvc
class ApplicationPageSmokeTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void mainPagesRenderWithDefaultProfile() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("index"));

        mockMvc.perform(get("/assessment"))
                .andExpect(status().isOk())
                .andExpect(view().name("assessment"));

        mockMvc.perform(get("/survey"))
                .andExpect(status().isOk())
                .andExpect(view().name("survey"));

        mockMvc.perform(get("/journal"))
                .andExpect(status().isOk())
                .andExpect(view().name("journal"));

        mockMvc.perform(get("/support"))
                .andExpect(status().isOk())
                .andExpect(view().name("support"));

        mockMvc.perform(get("/about"))
                .andExpect(status().isOk())
                .andExpect(view().name("about"));
    }
}
