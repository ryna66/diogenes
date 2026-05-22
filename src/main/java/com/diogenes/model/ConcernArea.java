package com.diogenes.model;

public enum ConcernArea {
    DEPRESSION(
            "Depression",
            "Low mood, loss of interest, hopelessness, or reduced energy feel like the clearest concern.",
            "PHQ-9 (Patient Health Questionnaire)",
            "9 items that screen specifically for depression severity using standard diagnostic criteria.",
            "Best when you want a focused depression screen with a score that is easy to track over time.",
            9
    ),
    ANXIETY(
            "Anxiety",
            "Worry, panic, nervousness, or feeling on edge feels like the main problem to clarify.",
            "GAD-7 (Generalized Anxiety Disorder)",
            "7 items that reliably identify the severity of generalized anxiety and panic symptoms.",
            "Best when anxiety is the clearest pattern and you want a short, dependable severity measure.",
            7
    ),
    OVERALL_WELL_BEING(
            "Overall Well-being",
            "You want to understand positive well-being, not only symptoms or illness.",
            "WHO-5 (Well-Being Index)",
            "5 items focused entirely on positive mental well-being rather than diagnosing illness.",
            "Best when you want a strengths-based snapshot of how well you have been feeling lately.",
            5
    );

    private final String label;
    private final String description;
    private final String surveyName;
    private final String keyFeatures;
    private final String recommendationSummary;
    private final int questionCount;

    ConcernArea(String label,
                String description,
                String surveyName,
                String keyFeatures,
                String recommendationSummary,
                int questionCount) {
        this.label = label;
        this.description = description;
        this.surveyName = surveyName;
        this.keyFeatures = keyFeatures;
        this.recommendationSummary = recommendationSummary;
        this.questionCount = questionCount;
    }

    public String getLabel() {
        return label;
    }

    public String getDescription() {
        return description;
    }

    public String getSurveyName() {
        return surveyName;
    }

    public String getKeyFeatures() {
        return keyFeatures;
    }

    public String getRecommendationSummary() {
        return recommendationSummary;
    }

    public int getQuestionCount() {
        return questionCount;
    }

    public String[] getQuestionPrompts() {
        return switch (this) {
            case DEPRESSION -> new String[]{
                    "Little interest or pleasure in doing things",
                    "Feeling down, depressed, or hopeless",
                    "Trouble falling or staying asleep, or sleeping too much",
                    "Feeling tired or having little energy",
                    "Poor appetite or overeating",
                    "Feeling bad about yourself, or that you are a failure",
                    "Trouble concentrating on things",
                    "Moving or speaking slowly, or being fidgety or restless",
                    "Thoughts that you would be better off dead or of hurting yourself"
            };
            case ANXIETY -> new String[]{
                    "Feeling nervous, anxious, or on edge",
                    "Not being able to stop or control worrying",
                    "Worrying too much about different things",
                    "Trouble relaxing",
                    "Being so restless that it is hard to sit still",
                    "Becoming easily annoyed or irritable",
                    "Feeling afraid as if something awful might happen"
            };
            case OVERALL_WELL_BEING -> new String[]{
                    "I have felt cheerful and in good spirits",
                    "I have felt calm and relaxed",
                    "I have felt active and vigorous",
                    "I woke up feeling fresh and rested",
                    "My daily life has been filled with things that interest me"
            };
        };
    }
}
