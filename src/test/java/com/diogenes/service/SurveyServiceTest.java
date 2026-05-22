package com.diogenes.service;

import com.diogenes.model.AssessmentForm;
import com.diogenes.model.ConcernArea;
import com.diogenes.model.SurveyResult;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class SurveyServiceTest {

    private final SurveyService surveyService = new SurveyService();

    @Test
    void analyze_scoresPhq9AndFlagsItemNineRisk() {
        AssessmentForm form = buildForm(ConcernArea.DEPRESSION, 1, 2, 1, 3, 0, 1, 1, 0, 1);

        SurveyResult analyzed = surveyService.analyze(form);

        assertThat(analyzed.getSurveyName()).isEqualTo("PHQ-9 (Patient Health Questionnaire)");
        assertThat(analyzed.getTotalScore()).isEqualTo(10);
        assertThat(analyzed.getSeverityLabel()).isEqualTo("Moderate depression");
        assertThat(analyzed.isUrgentSupportSuggested()).isTrue();
        assertThat(analyzed.getRecommendation()).contains("moderate level").contains("self-harm");
    }

    @Test
    void analyze_scoresGad7() {
        AssessmentForm form = buildForm(ConcernArea.ANXIETY, 3, 2, 2, 1, 1, 0, 2);

        SurveyResult analyzed = surveyService.analyze(form);

        assertThat(analyzed.getSurveyName()).isEqualTo("GAD-7 (Generalized Anxiety Disorder)");
        assertThat(analyzed.getTotalScore()).isEqualTo(11);
        assertThat(analyzed.getSeverityLabel()).isEqualTo("Moderate anxiety");
        assertThat(analyzed.isUrgentSupportSuggested()).isFalse();
    }

    @Test
    void analyze_scoresWho5AndCalculatesPercentage() {
        AssessmentForm form = buildForm(ConcernArea.OVERALL_WELL_BEING, 5, 4, 3, 2, 1);

        SurveyResult analyzed = surveyService.analyze(form);

        assertThat(analyzed.getSurveyName()).isEqualTo("WHO-5 (Well-Being Index)");
        assertThat(analyzed.getTotalScore()).isEqualTo(15);
        assertThat(analyzed.getPercentageScore()).isEqualTo(60);
        assertThat(analyzed.getSeverityLabel()).isEqualTo("Positive well-being");
    }

    private AssessmentForm buildForm(ConcernArea concernArea, Integer... responses) {
        AssessmentForm form = new AssessmentForm();
        form.setConcernArea(concernArea);
        Integer[] values = new Integer[9];
        System.arraycopy(responses, 0, values, 0, responses.length);
        form.setResponses(values);
        return form;
    }
}
