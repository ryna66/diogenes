package com.diogenes.service;

import com.diogenes.model.JournalEntry;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class JournalInsightServiceTest {

    private final JournalInsightService journalInsightService = new JournalInsightService();

    @Test
    void analyze_setsLowStressForMinimumInputs() {
        JournalEntry entry = buildEntry(1, 1, 1, 1);

        JournalEntry analyzed = journalInsightService.analyze(entry);

        assertThat(analyzed).isSameAs(entry);
        assertThat(analyzed.getStressScore()).isEqualTo(2);
        assertThat(analyzed.getStressCategory()).isEqualTo("Low Stress");
    }

    @Test
    void analyze_setsModerateStressAtBoundary() {
        JournalEntry entry = buildEntry(3, 3, 3, 3);

        JournalEntry analyzed = journalInsightService.analyze(entry);

        assertThat(analyzed.getStressScore()).isEqualTo(6);
        assertThat(analyzed.getStressCategory()).isEqualTo("Moderate Stress");
    }

    @Test
    void analyze_setsHighStressAtBoundary() {
        JournalEntry entry = buildEntry(4, 4, 4, 4);

        JournalEntry analyzed = journalInsightService.analyze(entry);

        assertThat(analyzed.getStressScore()).isEqualTo(8);
        assertThat(analyzed.getStressCategory()).isEqualTo("High Stress");
    }

    @Test
    void analyze_setsVeryHighStressForMaximumInputs() {
        JournalEntry entry = buildEntry(5, 5, 5, 5);

        JournalEntry analyzed = journalInsightService.analyze(entry);

        assertThat(analyzed.getStressScore()).isEqualTo(10);
        assertThat(analyzed.getStressCategory()).isEqualTo("Very High Stress");
    }

    private JournalEntry buildEntry(int workloadPressure,
                                    int sleepDifficulty,
                                    int focusDifficulty,
                                    int supportDifficulty) {
        JournalEntry entry = new JournalEntry();
        entry.setWorkloadPressure(workloadPressure);
        entry.setSleepDifficulty(sleepDifficulty);
        entry.setFocusDifficulty(focusDifficulty);
        entry.setSupportDifficulty(supportDifficulty);
        return entry;
    }
}
