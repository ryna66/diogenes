package com.diogenes.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
public class JournalEntry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Please choose the journal date")
    private LocalDate entryDate;

    @NotNull(message = "Mood is required")
    @Enumerated(EnumType.STRING)
    private Mood mood;

    @NotNull(message = "Please rate how manageable your workload felt")
    @Min(value = 1, message = "Choose a value between 1 and 5")
    @Max(value = 5, message = "Choose a value between 1 and 5")
    private Integer workloadPressure;

    @NotNull(message = "Please rate your sleep quality")
    @Min(value = 1, message = "Choose a value between 1 and 5")
    @Max(value = 5, message = "Choose a value between 1 and 5")
    private Integer sleepDifficulty;

    @NotNull(message = "Please rate your focus difficulty")
    @Min(value = 1, message = "Choose a value between 1 and 5")
    @Max(value = 5, message = "Choose a value between 1 and 5")
    private Integer focusDifficulty;

    @NotNull(message = "Please rate how supported you felt")
    @Min(value = 1, message = "Choose a value between 1 and 5")
    @Max(value = 5, message = "Choose a value between 1 and 5")
    private Integer supportDifficulty;

    private Integer stressScore;

    private String stressCategory;

    @NotBlank(message = "Please write a short reflection")
    @Column(length = 1000)
    private String note;

    private LocalDateTime createdAt;

    @PrePersist
    void onCreate() {
        if (entryDate == null) {
            entryDate = LocalDate.now();
        }
        createdAt = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getEntryDate() {
        return entryDate;
    }

    public void setEntryDate(LocalDate entryDate) {
        this.entryDate = entryDate;
    }

    public Mood getMood() {
        return mood;
    }

    public void setMood(Mood mood) {
        this.mood = mood;
    }

    public Integer getWorkloadPressure() {
        return workloadPressure;
    }

    public void setWorkloadPressure(Integer workloadPressure) {
        this.workloadPressure = workloadPressure;
    }

    public Integer getSleepDifficulty() {
        return sleepDifficulty;
    }

    public void setSleepDifficulty(Integer sleepDifficulty) {
        this.sleepDifficulty = sleepDifficulty;
    }

    public Integer getFocusDifficulty() {
        return focusDifficulty;
    }

    public void setFocusDifficulty(Integer focusDifficulty) {
        this.focusDifficulty = focusDifficulty;
    }

    public Integer getSupportDifficulty() {
        return supportDifficulty;
    }

    public void setSupportDifficulty(Integer supportDifficulty) {
        this.supportDifficulty = supportDifficulty;
    }

    public Integer getStressScore() {
        return stressScore;
    }

    public void setStressScore(Integer stressScore) {
        this.stressScore = stressScore;
    }

    public String getStressCategory() {
        return stressCategory;
    }

    public void setStressCategory(String stressCategory) {
        this.stressCategory = stressCategory;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
