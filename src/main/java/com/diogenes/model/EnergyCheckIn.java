package com.diogenes.model;

public enum EnergyCheckIn {
    RUNNING_ON_EMPTY("Running on empty", 1),
    A_BIT_DRAINED("A bit drained", 2),
    DOING_OKAY("Doing okay", 4),
    FEELING_STEADY("Feeling steady", 5);

    private final String label;
    private final int score;

    EnergyCheckIn(String label, int score) {
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
