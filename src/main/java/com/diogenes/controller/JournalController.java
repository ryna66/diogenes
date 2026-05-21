package com.diogenes.controller;

import com.diogenes.model.JournalEntry;
import com.diogenes.model.Mood;
import com.diogenes.repository.JournalEntryRepository;
import com.diogenes.service.JournalInsightService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class JournalController {

    private final JournalEntryRepository journalEntryRepository;
    private final JournalInsightService journalInsightService;

    public JournalController(JournalEntryRepository journalEntryRepository,
                             JournalInsightService journalInsightService) {
        this.journalEntryRepository = journalEntryRepository;
        this.journalInsightService = journalInsightService;
    }

    @GetMapping("/journal")
    public String journalPage(Model model) {
        if (!model.containsAttribute("journalEntry")) {
            JournalEntry journalEntry = new JournalEntry();
            journalEntry.setEntryDate(java.time.LocalDate.now());
            model.addAttribute("journalEntry", journalEntry);
        }
        model.addAttribute("moods", Mood.values());
        model.addAttribute("entries", journalEntryRepository.findTop5ByOrderByEntryDateDescCreatedAtDesc());
        return "journal";
    }

    @PostMapping("/journal")
    public String saveJournal(@Valid @ModelAttribute("journalEntry") JournalEntry journalEntry,
                              BindingResult bindingResult,
                              Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("moods", Mood.values());
            model.addAttribute("entries", journalEntryRepository.findTop5ByOrderByEntryDateDescCreatedAtDesc());
            return "journal";
        }

        journalInsightService.analyze(journalEntry);
        journalEntryRepository.save(journalEntry);
        return "redirect:/journal?success";
    }
}
