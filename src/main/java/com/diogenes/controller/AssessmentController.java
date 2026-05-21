package com.diogenes.controller;

import com.diogenes.model.AssessmentResult;
import com.diogenes.model.AgeGroup;
import com.diogenes.model.AnxietyCheckIn;
import com.diogenes.model.EnergyCheckIn;
import com.diogenes.model.SupportCheckIn;
import com.diogenes.repository.AssessmentResultRepository;
import com.diogenes.service.AssessmentService;
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

    public AssessmentController(AssessmentService assessmentService,
                                AssessmentResultRepository assessmentResultRepository) {
        this.assessmentService = assessmentService;
        this.assessmentResultRepository = assessmentResultRepository;
    }

    @ModelAttribute("ageGroups")
    public AgeGroup[] ageGroups() {
        return AgeGroup.values();
    }

    @ModelAttribute("energyOptions")
    public EnergyCheckIn[] energyOptions() {
        return EnergyCheckIn.values();
    }

    @ModelAttribute("anxietyOptions")
    public AnxietyCheckIn[] anxietyOptions() {
        return AnxietyCheckIn.values();
    }

    @ModelAttribute("supportOptions")
    public SupportCheckIn[] supportOptions() {
        return SupportCheckIn.values();
    }

    @GetMapping("/assessment")
    public String assessmentPage(Model model) {
        if (!model.containsAttribute("assessmentResult")) {
            model.addAttribute("assessmentResult", new AssessmentResult());
        }
        model.addAttribute("latestResult", assessmentResultRepository.findTopByOrderByCreatedAtDesc().orElse(null));
        return "assessment";
    }

    @PostMapping("/assessment")
    public String submitAssessment(@Valid @ModelAttribute("assessmentResult") AssessmentResult assessmentResult,
                                   BindingResult bindingResult,
                                   Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("latestResult", assessmentResultRepository.findTopByOrderByCreatedAtDesc().orElse(null));
            return "assessment";
        }

        assessmentService.analyze(assessmentResult);
        assessmentResultRepository.save(assessmentResult);
        return "redirect:/assessment?success";
    }
}
