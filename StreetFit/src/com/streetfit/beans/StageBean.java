package com.streetfit.beans;

import java.util.Date;

public class StageBean {

    private String title;
    private String itinerary;
    private String category;
    private Date date; // Date format to match SQL DATE
    private String place;
    private String intensity; // Enum values: 'bassa', 'media', 'alta'
    private int maxParticipants;

    // Constructor
    public StageBean(String title, String itinerary, String category, Date date, String place, String intensity, int maxParticipants) {
        this.title = title;
        this.itinerary = itinerary;
        this.category = category;
        this.date = date;
        this.place = place;
        this.intensity = intensity;
        this.maxParticipants = maxParticipants;
    }

    // Getters and Setters
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getItinerary() {
        return itinerary;
    }

    public void setItinerary(String itinerary) {
        this.itinerary = itinerary;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getIntensity() {
        return intensity;
    }

    public void setIntensity(String intensity) {
        this.intensity = intensity;
    }

    public int getMaxParticipants() {
        return maxParticipants;
    }

    public void setMaxParticipants(int maxParticipants) {
        this.maxParticipants = maxParticipants;
    }

    // Validation Methods
    public boolean isValid() {
        return validateTitle() && validateItinerary() && validateCategory() && validateDate() && validatePlace() && validateIntensity() && validateMaxParticipants();
    }

    private boolean validateTitle() {
        return title != null && !title.trim().isEmpty();
    }

    private boolean validateItinerary() {
        return itinerary != null && !itinerary.trim().isEmpty();
    }

    private boolean validateCategory() {
        return category != null && (category.equalsIgnoreCase("Functional") || category.equalsIgnoreCase("Yoga") || category.equalsIgnoreCase("Dance")|| category.equalsIgnoreCase("Stretching")||category.equalsIgnoreCase("HIIT") ||category.equalsIgnoreCase("Pilates"));
    }

    private boolean validateDate() {
        return date != null && !date.before(new Date()); // Ensures the date is not in the past
    }

    private boolean validatePlace() {
        return place != null && !place.trim().isEmpty();
    }

    private boolean validateIntensity() {
        return intensity != null && (intensity.equalsIgnoreCase("low") || intensity.equalsIgnoreCase("medium") || intensity.equalsIgnoreCase("high"));
    }

    private boolean validateMaxParticipants() {
        return maxParticipants > 0;
    }

}
