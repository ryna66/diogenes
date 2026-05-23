package com.diogenes.controller;

import com.diogenes.model.JournalEntry;
import com.diogenes.model.Mood;
import com.diogenes.repository.JournalEntryRepository;
import com.diogenes.service.JournalInsightService;
import com.diogenes.service.UserContentService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.time.LocalDate;

@Controller
public class JournalController {

    private final JournalEntryRepository journalEntryRepository;
    private final JournalInsightService journalInsightService;
    private final UserContentService userContentService;

    public JournalController(JournalEntryRepository journalEntryRepository,
                             JournalInsightService journalInsightService,
                             UserContentService userContentService) {
        this.journalEntryRepository = journalEntryRepository;
        this.journalInsightService = journalInsightService;
        this.userContentService = userContentService;
    }

    @GetMapping("/journal")
    public String journalPage(Model model) {
        prepareJournalForm(model);
        return "journal";
    }

    @PostMapping("/journal")
    public String saveJournal(@Valid @ModelAttribute("journalEntry") JournalEntry journalEntry,
                              BindingResult bindingResult,
                              Model model) {
        if (bindingResult.hasErrors()) {
            prepareJournalForm(model);
            return "journal";
        }

        journalEntry.setUser(userContentService.currentUser());
        journalInsightService.analyze(journalEntry);
        journalEntryRepository.save(journalEntry);
        return "redirect:/journal?success";
    }

    private void prepareJournalForm(Model model) {
        if (!model.containsAttribute("journalEntry")) {
            JournalEntry journalEntry = new JournalEntry();
            journalEntry.setEntryDate(LocalDate.now());
            model.addAttribute("journalEntry", journalEntry);
        }
        model.addAttribute("moods", Mood.values());
        model.addAttribute("entries", userContentService.recentJournalEntries());
        model.addAttribute("activePage", "journal");
    }
}
