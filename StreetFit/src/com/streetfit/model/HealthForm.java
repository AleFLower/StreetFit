package com.streetfit.model;



public class HealthForm {
    private boolean hasInjuries;
    private boolean hasHeartIssues;
    private boolean isPhysicallyFit;

    public HealthForm(boolean hasInjuries, boolean hasHeartIssues, boolean isPhysicallyFit) {
        this.hasInjuries = hasInjuries;
        this.hasHeartIssues = hasHeartIssues;
        this.isPhysicallyFit = isPhysicallyFit;
    }

    public boolean hasInjuries() {
        return hasInjuries;
    }

    public boolean hasHeartIssues() {
        return hasHeartIssues;
    }

    public boolean isPhysicallyFit() {
        return isPhysicallyFit;
    }
}
