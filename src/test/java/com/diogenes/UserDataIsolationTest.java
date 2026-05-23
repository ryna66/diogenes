package com.diogenes;

import com.diogenes.model.AppUser;
import com.diogenes.model.JournalEntry;
import com.diogenes.model.Mood;
import com.diogenes.model.RegisterForm;
import com.diogenes.repository.JournalEntryRepository;
import com.diogenes.service.UserAccountService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.not;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class UserDataIsolationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserAccountService userAccountService;

    @Autowired
    private JournalEntryRepository journalEntryRepository;

    @Test
    void usersOnlySeeTheirOwnJournalEntries() throws Exception {
        AppUser userA = userAccountService.register(buildForm("user-a@example.com", "User A"));
        AppUser userB = userAccountService.register(buildForm("user-b@example.com", "User B"));

        journalEntryRepository.save(buildEntry(userA, "User A private note", LocalDate.of(2026, 5, 20)));
        journalEntryRepository.save(buildEntry(userB, "User B private note", LocalDate.of(2026, 5, 21)));

        MockHttpSession sessionA = login("user-a@example.com");
        mockMvc.perform(get("/journal").session(sessionA))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("User A private note")))
                .andExpect(content().string(not(containsString("User B private note"))));
    }

    private RegisterForm buildForm(String email, String displayName) {
        RegisterForm form = new RegisterForm();
        form.setEmail(email);
        form.setDisplayName(displayName);
        form.setPassword("password123");
        form.setConfirmPassword("password123");
        return form;
    }

    private JournalEntry buildEntry(AppUser user, String note, LocalDate entryDate) {
        JournalEntry entry = new JournalEntry();
        entry.setUser(user);
        entry.setEntryDate(entryDate);
        entry.setMood(Mood.OKAY);
        entry.setWorkloadPressure(2);
        entry.setSleepDifficulty(2);
        entry.setFocusDifficulty(2);
        entry.setSupportDifficulty(2);
        entry.setNote(note);
        entry.setStressScore(4);
        entry.setStressCategory("Low Stress");
        return entry;
    }

    private MockHttpSession login(String email) throws Exception {
        MockHttpSession session = new MockHttpSession();
        mockMvc.perform(post("/login")
                        .with(csrf())
                        .session(session)
                        .param("email", email)
                        .param("password", "password123"))
                .andExpect(status().is3xxRedirection());
        return session;
    }
}
