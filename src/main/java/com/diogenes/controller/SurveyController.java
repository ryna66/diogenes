package com.diogenes.controller;

import com.diogenes.model.AssessmentForm;
import com.diogenes.model.SurveyResult;
import com.diogenes.repository.SurveyResultRepository;
import com.diogenes.service.SurveyService;
import com.diogenes.service.UserContentService;
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
    private final UserContentService userContentService;

    public SurveyController(SurveyService surveyService,
                            SurveyResultRepository surveyResultRepository,
                            UserContentService userContentService) {
        this.surveyService = surveyService;
        this.surveyResultRepository = surveyResultRepository;
        this.userContentService = userContentService;
    }

    @GetMapping("/survey")
    public String surveyPage(Model model) {
        prepareSurveyPage(model);
        return "survey";
    }

    @PostMapping("/survey")
    public String submitSurvey(@Valid @ModelAttribute("surveyForm") AssessmentForm surveyForm,
                               BindingResult bindingResult,
                               Model model) {
        validateSurveyResponses(surveyForm, bindingResult);

        if (bindingResult.hasErrors()) {
            prepareSurveyPage(model);
            return "survey";
        }

        SurveyResult analyzed = surveyService.analyze(surveyForm);
        analyzed.setUser(userContentService.currentUser());
        surveyResultRepository.save(analyzed);
        return "redirect:/survey?success";
    }

    private void validateSurveyResponses(AssessmentForm surveyForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return;
        }
        try {
            surveyService.validateResponses(surveyForm);
        } catch (IllegalArgumentException exception) {
            bindingResult.reject("responses", exception.getMessage());
        }
    }

    private void prepareSurveyPage(Model model) {
        if (!model.containsAttribute("surveyForm")) {
            model.addAttribute("surveyForm", new AssessmentForm());
        }
        model.addAttribute("latestSurvey", userContentService.latestSurvey().orElse(null));
        model.addAttribute("activePage", "survey");
    }
}
