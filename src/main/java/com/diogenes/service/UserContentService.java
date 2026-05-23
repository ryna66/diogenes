package com.diogenes.service;

import com.diogenes.model.AppUser;
import com.diogenes.model.AssessmentResult;
import com.diogenes.model.JournalEntry;
import com.diogenes.model.SurveyResult;
import com.diogenes.repository.AssessmentResultRepository;
import com.diogenes.repository.JournalEntryRepository;
import com.diogenes.repository.SurveyResultRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserContentService {

    private final CurrentUserService currentUserService;
    private final JournalEntryRepository journalEntryRepository;
    private final AssessmentResultRepository assessmentResultRepository;
    private final SurveyResultRepository surveyResultRepository;

    public UserContentService(CurrentUserService currentUserService,
                              JournalEntryRepository journalEntryRepository,
                              AssessmentResultRepository assessmentResultRepository,
                              SurveyResultRepository surveyResultRepository) {
        this.currentUserService = currentUserService;
        this.journalEntryRepository = journalEntryRepository;
        this.assessmentResultRepository = assessmentResultRepository;
        this.surveyResultRepository = surveyResultRepository;
    }

    public AppUser currentUser() {
        return currentUserService.requireCurrentUser();
    }

    public List<JournalEntry> recentJournalEntries() {
        return journalEntryRepository.findTop5ByUserOrderByEntryDateDescCreatedAtDesc(currentUser());
    }

    public Optional<AssessmentResult> latestAssessment() {
        return assessmentResultRepository.findTopByUserOrderByCreatedAtDesc(currentUser());
    }

    public Optional<SurveyResult> latestSurvey() {
        return surveyResultRepository.findTopByUserOrderByCreatedAtDesc(currentUser());
    }

    public long journalCount() {
        return journalEntryRepository.countByUser(currentUser());
    }

    public long assessmentCount() {
        return assessmentResultRepository.countByUser(currentUser());
    }

    public long surveyCount() {
        return surveyResultRepository.countByUser(currentUser());
    }
}
