package com.diogenes.controller;

import com.diogenes.model.AssessmentResult;
import com.diogenes.repository.AssessmentResultRepository;
import com.diogenes.service.AssessmentService;
import com.diogenes.service.UserContentService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AssessmentController {

    private final AssessmentService assessmentService;
    private final AssessmentResultRepository assessmentResultRepository;
    private final UserContentService userContentService;

    public AssessmentController(AssessmentService assessmentService,
                                AssessmentResultRepository assessmentResultRepository,
                                UserContentService userContentService) {
        this.assessmentService = assessmentService;
        this.assessmentResultRepository = assessmentResultRepository;
        this.userContentService = userContentService;
    }

    @GetMapping("/assessment")
    public String assessmentPage(Model model) {
        prepareAssessmentPage(model);
        return "assessment";
    }

    @PostMapping("/assessment")
    public String submitAssessment(@Valid @ModelAttribute("assessmentResult") AssessmentResult assessmentResult,
                                   BindingResult bindingResult,
                                   Model model) {
        if (bindingResult.hasErrors()) {
            prepareAssessmentPage(model);
            return "assessment";
        }

        assessmentResult.setUser(userContentService.currentUser());
        assessmentService.analyze(assessmentResult);
        assessmentResultRepository.save(assessmentResult);
        return "redirect:/assessment?success";
    }

    private void prepareAssessmentPage(Model model) {
        if (!model.containsAttribute("assessmentResult")) {
            model.addAttribute("assessmentResult", new AssessmentResult());
        }
        model.addAttribute("latestResult", userContentService.latestAssessment().orElse(null));
        model.addAttribute("activePage", "assessment");
    }
}
