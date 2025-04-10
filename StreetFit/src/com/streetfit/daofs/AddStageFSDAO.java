package com.streetfit.daofs;

import com.streetfit.dao.AddStageDao;
import com.streetfit.model.Stage;

import com.streetfit.exception.DAOException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.BufferedWriter;
import java.util.Date;

public class AddStageFSDAO implements AddStageDao {

    // Path to the CSV file
    private static final String CSV_FILE = "res/stages.csv";

    @Override
    public void addStage(Stage stage) throws DAOException {

        String title = stage.getTitle();
        String itinerary = stage.getItinerary();
        String category = stage.getCategory();
        Date date = stage.getDate();
        String location = stage.getLocation();
        String intensity = stage.getIntensity();
        int maxParticipants = stage.getMaxParticipants();

        // Convert date to string format (optional: you can use java.text.SimpleDateFormat)
        String dateString = String.format("%tF", date); // Simple format like "2025-04-10"

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(CSV_FILE, true))) {
            // Write the data to CSV file in the format: title, itinerary, category, date, location, intensity, maxParticipants
            writer.write(String.format("%s,%s,%s,%s,%s,%s,%d%n", title, itinerary, category, dateString, location, intensity, maxParticipants));
        } catch (IOException e) {
            throw new DAOException("Error writing stage data to file: " + e.getMessage(), e);
        }
    }
}

