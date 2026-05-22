package com.diogenes.service;

import java.time.LocalDate;

public record WeeklyJournalTrend(
        LocalDate weekStart,
        LocalDate weekEnd,
        double averageStress,
        String dominantMoodLabel,
        int entryCount
) {
    public LocalDate getWeekStart() {
        return weekStart;
    }

    public LocalDate getWeekEnd() {
        return weekEnd;
    }

    public double getAverageStress() {
        return averageStress;
    }

    public String getDominantMoodLabel() {
        return dominantMoodLabel;
    }

    public int getEntryCount() {
        return entryCount;
    }
}
