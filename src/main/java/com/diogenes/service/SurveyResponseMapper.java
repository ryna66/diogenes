package com.diogenes.service;

import com.diogenes.model.SurveyResult;

final class SurveyResponseMapper {

    private SurveyResponseMapper() {
    }

    static void copyResponses(SurveyResult result, Integer[] responses, int questionCount) {
        if (questionCount > 0) {
            result.setResponse1(responses[0]);
        }
        if (questionCount > 1) {
            result.setResponse2(responses[1]);
        }
        if (questionCount > 2) {
            result.setResponse3(responses[2]);
        }
        if (questionCount > 3) {
            result.setResponse4(responses[3]);
        }
        if (questionCount > 4) {
            result.setResponse5(responses[4]);
        }
        if (questionCount > 5) {
            result.setResponse6(responses[5]);
        }
        if (questionCount > 6) {
            result.setResponse7(responses[6]);
        }
        if (questionCount > 7) {
            result.setResponse8(responses[7]);
        }
        if (questionCount > 8) {
            result.setResponse9(responses[8]);
        }
    }
}
