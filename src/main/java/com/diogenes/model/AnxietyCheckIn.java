package com.diogenes.model;

public enum AnxietyCheckIn {
    CALM("Relatively calm", 5),
    A_LITTLE_ON_EDGE("A little on edge", 4),
    CARRYING_A_LOT("Carrying a lot", 2),
    OVERWHELMED("Pretty overwhelmed", 0);

    private final String label;
    private final int score;

    AnxietyCheckIn(String label, int score) {
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
