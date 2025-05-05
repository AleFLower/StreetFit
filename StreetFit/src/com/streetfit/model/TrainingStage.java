package com.streetfit.model;

import java.util.Date;

public class TrainingStage {
    private String title;            // Title of the stage
    private String itinerary;        // Description of the itinerary
    private String category;         // Category of the stage (e.g., fitness, yoga, etc.)
    private Date date;               // Date of the stage
    private String location;         // Location of the stage
    private int maxParticipants;     // Maximum number of participants


    // Constructor to initialize all fields
    public TrainingStage(String title, String itinerary, String category, Date date, 
                 String location, int maxParticipants) {
        this.title = title;
        this.itinerary = itinerary;
        this.category = category;
        this.date = date;
        this.location = location;
        this.maxParticipants = maxParticipants;
    }

    // Getters
    public String getTitle() {
        return title;
    }

    public String getItinerary() {
        return itinerary;
    }

    public String getCategory() {
        return category;
    }

    public Date getDate() {
        return date;
    }

    public String getLocation() {
        return location;
    }


    public int getMaxParticipants() {
        return maxParticipants;
    }

    // Setters
    public void setTitle(String title) {
        this.title = title;
    }

    public void setItinerary(String itinerary) {
        this.itinerary = itinerary;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setMaxParticipants(int maxParticipants) {
        this.maxParticipants = maxParticipants;
    }

    // toString() to represent the object as a string
    @Override
    public String toString() {
        return "TrainingStage{" +
                "title='" + title + '\'' +
                ", itinerary='" + itinerary + '\'' +
                ", category='" + category + '\'' +
                ", date=" + date +
                ", location='" + location + '\'' +
                ", maxParticipants=" + maxParticipants +
                '}';
    }
}
