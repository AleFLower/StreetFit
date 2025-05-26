package main.java.com.streetfit.beans;


public class HealthFormBean {

    private boolean hasInjuries;
    private boolean hasHeartIssues;
    private boolean isPhysicallyFit;

    public HealthFormBean(boolean hasInjuries, boolean hasHeartIssues,boolean isPhysicallyFit ) {
	 this.hasInjuries = hasInjuries;
	 this.isPhysicallyFit = isPhysicallyFit;
	 this.hasHeartIssues = hasHeartIssues;
    }
 // Getters
    public boolean hasInjuries() {
        return hasInjuries;
    }

    public boolean hasHeartIssues() {
        return hasHeartIssues;
    }

    public boolean isPhysicallyFit() {
        return isPhysicallyFit;
    }

    // Setters
    public void setHasInjuries(boolean hasInjuries) {
        this.hasInjuries = hasInjuries;
    }

    public void setHasHeartIssues(boolean hasHeartIssues) {
        this.hasHeartIssues = hasHeartIssues;
    }

    public void setPhysicallyFit(boolean isPhysicallyFit) {
        this.isPhysicallyFit = isPhysicallyFit;
    }

   //validate user input
}
