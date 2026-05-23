package com.diogenes.controller.advice;

import com.diogenes.controller.AssessmentController;
import com.diogenes.model.AgeGroup;
import com.diogenes.model.AnxietyCheckIn;
import com.diogenes.model.EnergyCheckIn;
import com.diogenes.model.SupportCheckIn;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice(assignableTypes = AssessmentController.class)
public class AssessmentModelAdvice {

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
}
