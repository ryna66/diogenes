package com.diogenes.service;

import com.diogenes.model.AssessmentForm;
import com.diogenes.model.ConcernArea;
import com.diogenes.model.SurveyResult;
import org.springframework.stereotype.Service;

@Service
public class SurveyService {

    public SurveyResult analyze(AssessmentForm form) {
        ConcernArea concernArea = form.getConcernArea();
        int questionCount = concernArea.getQuestionCount();
        Integer[] responses = form.getResponses();

        int totalScore = 0;
        for (int index = 0; index < questionCount; index++) {
            totalScore += responses[index];
        }

        SurveyResult result = new SurveyResult();
        result.setConcernArea(concernArea);
        result.setSurveyName(concernArea.getSurveyName());
        result.setSurveyKeyFeatures(concernArea.getKeyFeatures());
        copyResponses(result, responses, questionCount);
        result.setTotalScore(totalScore);

        switch (concernArea) {
            case DEPRESSION -> scorePhq9(result, totalScore, responses[8]);
            case ANXIETY -> scoreGad7(result, totalScore);
            case OVERALL_WELL_BEING -> scoreWho5(result, totalScore);
            default -> throw new IllegalArgumentException("Unsupported concern area: " + concernArea);
        }

        return result;
    }

    public void validateResponses(AssessmentForm form) {
        if (form.getConcernArea() == null) {
            return;
        }

        int questionCount = form.getConcernArea().getQuestionCount();
        Integer[] responses = form.getResponses();
        if (responses == null) {
            throw new IllegalArgumentException("Please answer every question before submitting.");
        }

        int maxValue = form.getConcernArea() == ConcernArea.OVERALL_WELL_BEING ? 5 : 3;
        for (int index = 0; index < questionCount; index++) {
            Integer response = responses[index];
            if (response == null) {
                throw new IllegalArgumentException("Please answer question " + (index + 1) + ".");
            }
            if (response < 0 || response > maxValue) {
                throw new IllegalArgumentException("Question " + (index + 1) + " must be between 0 and " + maxValue + ".");
            }
        }
    }

    private void scorePhq9(SurveyResult result, int totalScore, Integer itemNineResponse) {
        boolean urgent = itemNineResponse != null && itemNineResponse > 0;
        result.setUrgentSupportSuggested(urgent);
        result.setPercentageScore(null);

        String severityLabel = switch (totalScore) {
            case 0, 1, 2, 3, 4 -> "Minimal depression";
            case 5, 6, 7, 8, 9 -> "Mild depression";
            case 10, 11, 12, 13, 14 -> "Moderate depression";
            case 15, 16, 17, 18, 19 -> "Moderately severe depression";
            default -> "Severe depression";
        };
        result.setSeverityLabel(severityLabel);
        result.setRecommendation(buildPhq9Recommendation(severityLabel, urgent));
    }

    private void scoreGad7(SurveyResult result, int totalScore) {
        result.setUrgentSupportSuggested(false);
        result.setPercentageScore(null);

        String severityLabel = switch (totalScore) {
            case 0, 1, 2, 3, 4 -> "Minimal anxiety";
            case 5, 6, 7, 8, 9 -> "Mild anxiety";
            case 10, 11, 12, 13, 14 -> "Moderate anxiety";
            default -> "Severe anxiety";
        };
        result.setSeverityLabel(severityLabel);
        result.setRecommendation(buildSymptomRecommendation(
                "anxiety",
                severityLabel,
                totalScore,
                "worry, restlessness, or feeling on edge"
        ));
    }

    private void scoreWho5(SurveyResult result, int totalScore) {
        result.setUrgentSupportSuggested(false);
        int percentageScore = totalScore * 4;
        result.setPercentageScore(percentageScore);

        String severityLabel;
        if (percentageScore <= 28) {
            severityLabel = "Poor well-being";
        } else if (percentageScore <= 50) {
            severityLabel = "Low well-being";
        } else if (percentageScore <= 72) {
            severityLabel = "Positive well-being";
        } else {
            severityLabel = "High well-being";
        }

        result.setSeverityLabel(severityLabel);
        result.setRecommendation(
                "Your WHO-5 score is "
                        + totalScore
                        + " out of 25 ("
                        + percentageScore
                        + "%), which suggests "
                        + severityLabel.toLowerCase()
                        + ". "
                        + ConcernArea.OVERALL_WELL_BEING.getRecommendationSummary()
                        + " Keep tracking what supports your mood, rest, and connection over the next couple of weeks."
        );
    }

    private String buildPhq9Recommendation(String severityLabel, boolean urgent) {
        String recommendation = buildSymptomRecommendation(
                "depression",
                severityLabel,
                -1,
                "low mood, low energy, or loss of interest"
        );

        if (urgent) {
            recommendation += " Because you indicated thoughts of self-harm or hurting yourself, please reach out for support immediately through a trusted person, counselor, or crisis line.";
        }

        return recommendation;
    }

    private String buildSymptomRecommendation(String symptomName,
                                                String severityLabel,
                                                int totalScore,
                                                String symptomDescription) {
        String severityBand = severityBandPhrase(severityLabel);
        String scoreDetail = totalScore >= 0 ? " (score " + totalScore + ")" : "";

        return "Your responses suggest a "
                + severityBand
                + " level of "
                + symptomName
                + " symptoms"
                + scoreDetail
                + ". This pattern may include "
                + symptomDescription
                + ". "
                + "This tool is for self-reflection only and does not replace professional diagnosis. "
                + "If symptoms persist or feel overwhelming, talk with a counselor, doctor, or someone you trust.";
    }

    private String severityBandPhrase(String severityLabel) {
        if (severityLabel.startsWith("Minimal")) {
            return "minimal";
        }
        if (severityLabel.startsWith("Mild")) {
            return "mild";
        }
        if (severityLabel.startsWith("Moderately severe") || severityLabel.startsWith("Moderate")) {
            return "moderate";
        }
        if (severityLabel.startsWith("Severe") || severityLabel.startsWith("Poor")
                || severityLabel.startsWith("Low")) {
            return "severe";
        }
        if (severityLabel.startsWith("Positive") || severityLabel.startsWith("High")) {
            return "positive";
        }
        return severityLabel.toLowerCase();
    }

    private void copyResponses(SurveyResult result, Integer[] responses, int questionCount) {
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
