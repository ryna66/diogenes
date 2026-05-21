package com.diogenes.service;

import com.diogenes.model.AgeGroup;
import com.diogenes.model.AssessmentResult;
import org.springframework.stereotype.Service;

@Service
public class AssessmentService {

    public AssessmentResult analyze(AssessmentResult assessment) {
        int score = 0;

        if (assessment.getSleepHours() >= 7) {
            score += 4;
        } else if (assessment.getSleepHours() >= 5) {
            score += 2;
        }

        score += assessment.getEnergyLevel().getScore();
        score += assessment.getAnxietyLevel().getScore();
        score += assessment.getSupportLevel().getScore();

        AgeGroup ageGroup = assessment.getAgeGroup();

        if (score >= 14) {
            assessment.setWellnessBand("Stable");
            assessment.setRecommendation(
                    "Your habits look fairly balanced. Keep focusing on "
                            + ageGroup.getGroundingFocus()
                            + ". Keep checking in with yourself and stay connected to "
                            + ageGroup.getTrustedSupport()
                            + "."
            );
        } else if (score >= 9) {
            assessment.setWellnessBand("Needs Attention");
            assessment.setRecommendation(
                    "Things may be feeling a bit heavy right now. Try centering "
                            + ageGroup.getGroundingFocus()
                            + ", reduce overload where you can, and talk to "
                            + ageGroup.getTrustedSupport()
                            + " if the pressure keeps building."
            );
        } else {
            assessment.setWellnessBand("High Support Suggested");
            assessment.setRecommendation(
                    "Your check-in suggests that extra support could really help right now. Please reach out to "
                            + ageGroup.getTrustedSupport()
                            + " and avoid carrying this alone. Starting with small stabilizing steps like "
                            + ageGroup.getGroundingFocus()
                            + " can help while you connect with support."
            );
        }

        return assessment;
    }
}
