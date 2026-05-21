package com.diogenes.model;

public enum SupportCheckIn {
    FEELING_ALONE("Feeling mostly on my own", 1),
    HAVE_A_FEW_PEOPLE("I have a few people I can lean on", 3),
    WELL_SUPPORTED("I feel well supported", 5);

    private final String label;
    private final int score;

    SupportCheckIn(String label, int score) {
        this.label = label;
        this.score = score;
    }

    public String getLabel() {
        return label;
    }

    public int getScore() {
        return score;
    }
}
