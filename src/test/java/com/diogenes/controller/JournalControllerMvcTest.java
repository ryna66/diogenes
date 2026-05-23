package com.diogenes.controller;

import com.diogenes.model.AppUser;
import com.diogenes.model.JournalEntry;
import com.diogenes.model.Mood;
import com.diogenes.repository.JournalEntryRepository;
import com.diogenes.service.JournalInsightService;
import com.diogenes.service.UserContentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@WebMvcTest(JournalController.class)
@AutoConfigureMockMvc(addFilters = false)
class JournalControllerMvcTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private JournalEntryRepository journalEntryRepository;

    @MockBean
    private JournalInsightService journalInsightService;

    @MockBean
    private UserContentService userContentService;

    private AppUser currentUser;

    @BeforeEach
    void setUp() {
        currentUser = new AppUser();
        currentUser.setId(1L);
        currentUser.setEmail("mvc@example.com");
        currentUser.setDisplayName("Mvc User");
        when(userContentService.currentUser()).thenReturn(currentUser);
    }

    @Test
    void journalPageRendersSuccessfully() throws Exception {
        JournalEntry recentEntry = new JournalEntry();
        recentEntry.setEntryDate(LocalDate.of(2026, 5, 22));
        recentEntry.setMood(Mood.OKAY);
        recentEntry.setStressCategory("Moderate Stress");
        recentEntry.setWorkloadPressure(3);
        recentEntry.setSleepDifficulty(3);
        recentEntry.setFocusDifficulty(3);
        recentEntry.setSupportDifficulty(3);
        recentEntry.setNote("Steady day.");

        when(userContentService.recentJournalEntries()).thenReturn(List.of(recentEntry));
        when(journalInsightService.analyze(any(JournalEntry.class))).thenAnswer(invocation -> invocation.getArgument(0));

        mockMvc.perform(get("/journal"))
                .andExpect(status().isOk())
                .andExpect(view().name("journal"));
    }
}
