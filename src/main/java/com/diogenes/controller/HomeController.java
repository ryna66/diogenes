package com.diogenes.controller;

import com.diogenes.service.DashboardService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    private final DashboardService dashboardService;

    public HomeController(DashboardService dashboardService) {
        this.dashboardService = dashboardService;
    }

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("journalCount", dashboardService.totalJournalEntries());
        model.addAttribute("assessmentCount", dashboardService.totalAssessments());
        return "index";
    }

    @GetMapping("/about")
    public String about(Model model) {
        model.addAttribute("features", new String[]{
                "A check-in you can save and come back to",
                "Journal entries with dates",
                "Light stress summaries from journal answers",
                "A support page with grounded next steps"
        });
        return "about";
    }

    @GetMapping("/community")
    public String community() {
        return "community";
    }
}
