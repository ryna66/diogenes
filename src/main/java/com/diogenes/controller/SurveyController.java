package com.diogenes.controller;

import com.diogenes.model.AssessmentForm;
import com.diogenes.model.ConcernArea;
import com.diogenes.model.SurveyResult;
import com.diogenes.repository.SurveyResultRepository;
import com.diogenes.service.SurveyService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class SurveyController {

    private final SurveyService surveyService;
    private final SurveyResultRepository surveyResultRepository;

    public SurveyController(SurveyService surveyService, SurveyResultRepository surveyResultRepository) {
        this.surveyService = surveyService;
        this.surveyResultRepository = surveyResultRepository;
    }

    @ModelAttribute("concernAreas")
    public ConcernArea[] concernAreas() {
        return ConcernArea.values();
    }

    @GetMapping("/survey")
    public String surveyPage(Model model) {
        if (!model.containsAttribute("surveyForm")) {
            model.addAttribute("surveyForm", new AssessmentForm());
        }
        model.addAttribute("latestSurvey", surveyResultRepository.findTopByOrderByCreatedAtDesc().orElse(null));
        return "survey";
    }

    @PostMapping("/survey")
    public String submitSurvey(@Valid @ModelAttribute("surveyForm") AssessmentForm surveyForm,
                               BindingResult bindingResult,
                               Model model) {
        if (!bindingResult.hasErrors()) {
            try {
                surveyService.validateResponses(surveyForm);
            } catch (IllegalArgumentException exception) {
                bindingResult.reject("responses", exception.getMessage());
            }
        }

        if (bindingResult.hasErrors()) {
            model.addAttribute("latestSurvey", surveyResultRepository.findTopByOrderByCreatedAtDesc().orElse(null));
            return "survey";
        }

        SurveyResult analyzed = surveyService.analyze(surveyForm);
        surveyResultRepository.save(analyzed);
        return "redirect:/survey?success";
    }
}
