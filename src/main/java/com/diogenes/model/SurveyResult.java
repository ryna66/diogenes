package com.diogenes.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;

import java.time.LocalDateTime;

@Entity
@Table(name = "survey_result")
public class SurveyResult {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private AppUser user;

    @Enumerated(EnumType.STRING)
    private ConcernArea concernArea;

    private String surveyName;

    @Column(length = 2000)
    private String surveyKeyFeatures;

    private Integer response1;
    private Integer response2;
    private Integer response3;
    private Integer response4;
    private Integer response5;
    private Integer response6;
    private Integer response7;
    private Integer response8;
    private Integer response9;

    private Integer totalScore;
    private Integer percentageScore;
    private String severityLabel;

    @Column(length = 2000)
    private String recommendation;

    private boolean urgentSupportSuggested;

    private LocalDateTime createdAt;

    @PrePersist
    void onCreate() {
        createdAt = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public AppUser getUser() {
        return user;
    }

    public void setUser(AppUser user) {
        this.user = user;
    }

    public ConcernArea getConcernArea() {
        return concernArea;
    }

    public void setConcernArea(ConcernArea concernArea) {
        this.concernArea = concernArea;
    }

    public String getSurveyName() {
        return surveyName;
    }

    public void setSurveyName(String surveyName) {
        this.surveyName = surveyName;
    }

    public String getSurveyKeyFeatures() {
        return surveyKeyFeatures;
    }

    public void setSurveyKeyFeatures(String surveyKeyFeatures) {
        this.surveyKeyFeatures = surveyKeyFeatures;
    }

    public Integer getResponse1() {
        return response1;
    }

    public void setResponse1(Integer response1) {
        this.response1 = response1;
    }

    public Integer getResponse2() {
        return response2;
    }

    public void setResponse2(Integer response2) {
        this.response2 = response2;
    }

    public Integer getResponse3() {
        return response3;
    }

    public void setResponse3(Integer response3) {
        this.response3 = response3;
    }

    public Integer getResponse4() {
        return response4;
    }

    public void setResponse4(Integer response4) {
        this.response4 = response4;
    }

    public Integer getResponse5() {
        return response5;
    }

    public void setResponse5(Integer response5) {
        this.response5 = response5;
    }

    public Integer getResponse6() {
        return response6;
    }

    public void setResponse6(Integer response6) {
        this.response6 = response6;
    }

    public Integer getResponse7() {
        return response7;
    }

    public void setResponse7(Integer response7) {
        this.response7 = response7;
    }

    public Integer getResponse8() {
        return response8;
    }

    public void setResponse8(Integer response8) {
        this.response8 = response8;
    }

    public Integer getResponse9() {
        return response9;
    }

    public void setResponse9(Integer response9) {
        this.response9 = response9;
    }

    public Integer getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(Integer totalScore) {
        this.totalScore = totalScore;
    }

    public Integer getPercentageScore() {
        return percentageScore;
    }

    public void setPercentageScore(Integer percentageScore) {
        this.percentageScore = percentageScore;
    }

    public String getSeverityLabel() {
        return severityLabel;
    }

    public void setSeverityLabel(String severityLabel) {
        this.severityLabel = severityLabel;
    }

    public String getRecommendation() {
        return recommendation;
    }

    public void setRecommendation(String recommendation) {
        this.recommendation = recommendation;
    }

    public boolean isUrgentSupportSuggested() {
        return urgentSupportSuggested;
    }

    public void setUrgentSupportSuggested(boolean urgentSupportSuggested) {
        this.urgentSupportSuggested = urgentSupportSuggested;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
