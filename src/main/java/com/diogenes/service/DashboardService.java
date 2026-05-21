package com.diogenes.service;

import com.diogenes.repository.AssessmentResultRepository;
import com.diogenes.repository.JournalEntryRepository;
import org.springframework.stereotype.Service;

@Service
public class DashboardService {

    private final JournalEntryRepository journalEntryRepository;
    private final AssessmentResultRepository assessmentResultRepository;

    public DashboardService(JournalEntryRepository journalEntryRepository,
                            AssessmentResultRepository assessmentResultRepository) {
        this.journalEntryRepository = journalEntryRepository;
        this.assessmentResultRepository = assessmentResultRepository;
    }

    public long totalJournalEntries() {
        return journalEntryRepository.count();
    }

    public long totalAssessments() {
        return assessmentResultRepository.count();
    }
}
