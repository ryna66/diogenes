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
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

@Entity
public class AssessmentResult {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private AppUser user;

    @NotNull(message = "Sleep hours are required")
    @Min(value = 0, message = "Sleep hours must be valid")
    @Max(value = 15, message = "Sleep hours must be valid")
    private Integer sleepHours;

    @NotNull(message = "Please choose an age group")
    @Enumerated(EnumType.STRING)
    private AgeGroup ageGroup;

    @NotNull(message = "Please choose the option that best matches your energy")
    @Enumerated(EnumType.STRING)
    private EnergyCheckIn energyLevel;

    @NotNull(message = "Please choose the option that best matches how your mind feels")
    @Enumerated(EnumType.STRING)
    private AnxietyCheckIn anxietyLevel;

    @NotNull(message = "Please choose the option that best matches your support right now")
    @Enumerated(EnumType.STRING)
    private SupportCheckIn supportLevel;

    @Column(length = 2000)
    private String recommendation;

    private String wellnessBand;

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

    public Integer getSleepHours() {
        return sleepHours;
    }

    public void setSleepHours(Integer sleepHours) {
        this.sleepHours = sleepHours;
    }

    public AgeGroup getAgeGroup() {
        return ageGroup;
    }

    public void setAgeGroup(AgeGroup ageGroup) {
        this.ageGroup = ageGroup;
    }

    public EnergyCheckIn getEnergyLevel() {
        return energyLevel;
    }

    public void setEnergyLevel(EnergyCheckIn energyLevel) {
        this.energyLevel = energyLevel;
    }

    public AnxietyCheckIn getAnxietyLevel() {
        return anxietyLevel;
    }

    public void setAnxietyLevel(AnxietyCheckIn anxietyLevel) {
        this.anxietyLevel = anxietyLevel;
    }

    public SupportCheckIn getSupportLevel() {
        return supportLevel;
    }

    public void setSupportLevel(SupportCheckIn supportLevel) {
        this.supportLevel = supportLevel;
    }

    public String getRecommendation() {
        return recommendation;
    }

    public void setRecommendation(String recommendation) {
        this.recommendation = recommendation;
    }

    public String getWellnessBand() {
        return wellnessBand;
    }

    public void setWellnessBand(String wellnessBand) {
        this.wellnessBand = wellnessBand;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
