package com.diogenes.service;

import java.util.List;

public record JournalAnalytics(
        int totalEntries,
        double recentAverageStress,
        String stressTrendLabel,
        String dominantMoodLabel,
        long highStressDays,
        List<WeeklyJournalTrend> weeklyTrends,
        boolean hasEntries,
        boolean hasComparisonWindow
) {
    public int getTotalEntries() {
        return totalEntries;
    }

    public double getRecentAverageStress() {
        return recentAverageStress;
    }

    public String getStressTrendLabel() {
        return stressTrendLabel;
    }

    public String getDominantMoodLabel() {
        return dominantMoodLabel;
    }

    public long getHighStressDays() {
        return highStressDays;
    }

    public List<WeeklyJournalTrend> getWeeklyTrends() {
        return weeklyTrends;
    }

    public boolean isHasEntries() {
        return hasEntries;
    }

    public boolean isHasComparisonWindow() {
        return hasComparisonWindow;
    }
}
