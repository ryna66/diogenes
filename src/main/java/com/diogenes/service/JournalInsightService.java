package com.diogenes.service;

import com.diogenes.model.JournalEntry;
import org.springframework.stereotype.Service;

@Service
public class JournalInsightService {

    public JournalEntry analyze(JournalEntry journalEntry) {
        int rawScore = journalEntry.getWorkloadPressure()
                + journalEntry.getSleepDifficulty()
                + journalEntry.getFocusDifficulty()
                + journalEntry.getSupportDifficulty();

        int stressScore = Math.max(1, Math.min(10, (int) Math.round(rawScore / 2.0)));
        journalEntry.setStressScore(stressScore);

        if (stressScore <= 3) {
            journalEntry.setStressCategory("Low Stress");
        } else if (stressScore <= 6) {
            journalEntry.setStressCategory("Moderate Stress");
        } else if (stressScore <= 8) {
            journalEntry.setStressCategory("High Stress");
        } else {
            journalEntry.setStressCategory("Very High Stress");
        }

        return journalEntry;
    }
}
