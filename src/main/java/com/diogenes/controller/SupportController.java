package com.diogenes.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SupportController {

    @GetMapping("/support")
    public String supportPage(Model model) {
        model.addAttribute("activePage", "support");
        return "support";
    }
}
