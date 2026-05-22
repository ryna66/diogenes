package com.diogenes.model;

import jakarta.validation.constraints.NotNull;

public class AssessmentForm {

    @NotNull(message = "Please choose a survey")
    private ConcernArea concernArea;

    private Integer[] responses = new Integer[9];

    public ConcernArea getConcernArea() {
        return concernArea;
    }

    public void setConcernArea(ConcernArea concernArea) {
        this.concernArea = concernArea;
    }

    public Integer[] getResponses() {
        return responses;
    }

    public void setResponses(Integer[] responses) {
        this.responses = responses;
    }
}
