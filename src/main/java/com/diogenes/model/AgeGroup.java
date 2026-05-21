package com.diogenes.model;

public enum AgeGroup {
    CHILD(
            "Child (under 13)",
            "Simple language and caregiver support matter most here.",
            "a parent, guardian, teacher, or school counselor",
            "gentle routines, sleep consistency, and regular play or downtime"
    ),
    TEEN(
            "Teen",
            "School pressure, friendships, and identity changes can all affect wellbeing.",
            "a parent, school counselor, favorite teacher, or trusted adult",
            "sleep, phone boundaries, and short reset breaks between responsibilities"
    ),
    YOUNG_ADULT(
            "Young adult",
            "Transitions around study, work, and independence can make stress feel heavy.",
            "a close friend, mentor, resident advisor, or campus counselor",
            "rest, realistic planning, and asking for help before things pile up"
    ),
    ADULT(
            "Adult",
            "Work, finances, caregiving, and daily responsibilities often stack together.",
            "a partner, friend, therapist, mentor, or primary care professional",
            "consistent routines, protected rest, and reducing overload where possible"
    ),
    OLDER_ADULT(
            "Older adult",
            "Energy shifts, health concerns, and life changes can shape emotional wellbeing.",
            "a family member, physician, counselor, or community support person",
            "steady sleep, light movement, social connection, and manageable daily rhythms"
    );

    private final String label;
    private final String summary;
    private final String trustedSupport;
    private final String groundingFocus;

    AgeGroup(String label, String summary, String trustedSupport, String groundingFocus) {
        this.label = label;
        this.summary = summary;
        this.trustedSupport = trustedSupport;
        this.groundingFocus = groundingFocus;
    }

    public String getLabel() {
        return label;
    }

    public String getSummary() {
        return summary;
    }

    public String getTrustedSupport() {
        return trustedSupport;
    }

    public String getGroundingFocus() {
        return groundingFocus;
    }
}
