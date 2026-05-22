package com.diogenes.controller;

import com.diogenes.model.JournalEntry;
import com.diogenes.model.Mood;
import com.diogenes.repository.JournalEntryRepository;
import com.diogenes.service.JournalInsightService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
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
class JournalControllerMvcTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private JournalEntryRepository journalEntryRepository;

    @MockBean
    private JournalInsightService journalInsightService;

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

        when(journalEntryRepository.findTop5ByOrderByEntryDateDescCreatedAtDesc())
                .thenReturn(List.of(recentEntry));
        when(journalInsightService.analyze(any(JournalEntry.class))).thenAnswer(invocation -> invocation.getArgument(0));

        mockMvc.perform(get("/journal"))
                .andExpect(status().isOk())
                .andExpect(view().name("journal"));
    }
}
