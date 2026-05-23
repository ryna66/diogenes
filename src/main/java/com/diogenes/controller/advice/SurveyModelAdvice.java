package com.diogenes.controller.advice;

import com.diogenes.controller.SurveyController;
import com.diogenes.model.ConcernArea;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice(assignableTypes = SurveyController.class)
public class SurveyModelAdvice {

    @ModelAttribute("concernAreas")
    public ConcernArea[] concernAreas() {
        return ConcernArea.values();
    }
}
