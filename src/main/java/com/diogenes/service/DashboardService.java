package com.diogenes.service;

import com.diogenes.repository.AssessmentResultRepository;
import com.diogenes.repository.JournalEntryRepository;
import com.diogenes.repository.SurveyResultRepository;
import org.springframework.stereotype.Service;

@Service
public class DashboardService {

    private final JournalEntryRepository journalEntryRepository;
    private final AssessmentResultRepository assessmentResultRepository;
    private final SurveyResultRepository surveyResultRepository;

    public DashboardService(JournalEntryRepository journalEntryRepository,
                            AssessmentResultRepository assessmentResultRepository,
                            SurveyResultRepository surveyResultRepository) {
        this.journalEntryRepository = journalEntryRepository;
        this.assessmentResultRepository = assessmentResultRepository;
        this.surveyResultRepository = surveyResultRepository;
    }

    public long totalJournalEntries() {
        return journalEntryRepository.count();
    }

    public long totalAssessments() {
        return assessmentResultRepository.count();
    }

    public long totalSurveys() {
        return surveyResultRepository.count();
    }
}
