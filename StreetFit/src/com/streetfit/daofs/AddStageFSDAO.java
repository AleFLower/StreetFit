package com.streetfit.daofs;

import com.streetfit.dao.AddStageDao;

import com.streetfit.model.TrainingStage;

import com.streetfit.exception.DAOException;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AddStageFSDAO implements AddStageDao {

    // Path to the CSV file
    private static final String CSV_FILE = "res/stages.csv";

    @Override
    public void addStage(TrainingStage stage) throws DAOException {
    	
    	List<TrainingStage> existingStages = getStages(); // carica stage esistenti

        for (TrainingStage existing : existingStages) {
        	if (existing.getTitle().equals(stage.getTitle())) {  //NOTA: == non confronta il contenuto delle stringhe, ma fa un confronto tra i riferimenti degli oggetti, ovvero verifica se le due variabili existing.getTitle() e stage.getTitle() puntano allo stesso oggetto in memoria.Quindi metto isequal()
        	    throw new DAOException("Stage already exists");
        	}
        }

        String title = stage.getTitle();
        String itinerary = stage.getItinerary();
        String category = stage.getCategory();
        Date date = stage.getDate();
        String location = stage.getLocation();
        int maxParticipants = stage.getMaxParticipants();

        // Convert date to string format (optional: you can use java.text.SimpleDateFormat)
        String dateString = String.format("%tF", date); // Simple format like "2025-04-10"

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(CSV_FILE, true))) {
            // Write the data to CSV file in the format: title, itinerary, category, date, location, intensity, maxParticipants
            writer.write(String.format("%s,%s,%s,%s,%s,%d%n", title, itinerary, category, dateString, location, maxParticipants));
        } catch (IOException e) {
            throw new DAOException("Error writing stage data to file: " + e.getMessage(), e);
        }
    }

	@Override
	public List<TrainingStage> getStages() throws DAOException {
		
		List <TrainingStage> stagelist = new ArrayList<TrainingStage>();
		
		  try (BufferedReader br = new BufferedReader(new FileReader(CSV_FILE))) {
	            String line;
	            boolean headerSkipped = false;

	            while ((line = br.readLine()) != null) {
	                // Una volta letta l'intestazione, la salto: ho fatto cosi:l'intestazione mi dice solamente i nomi delle colonne, come proprio nel database
	                if (!headerSkipped) {
	                    headerSkipped = true;
	                } else {
	                    String[] data = line.split(",");
	                    // Ignora righe malformate
	                    if (data.length != 6) {   //6 columns expected, like in a database.
	                        continue;
	                    }
	                    String name = data[0].trim();
	                    String itinerary = data[1].trim();
	                    String category = data[2].trim();
	                    String dateString = data[3].trim();
	                    String place = data[4].trim();
	                    int maxPart = Integer.parseInt(data[5].trim());
	                    
	                    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

	                    // Converti la stringa in Date
	                    Date date = format.parse(dateString);
	                    
	                    TrainingStage stage = new TrainingStage(name,itinerary,category,date,place,maxPart);
	                    stagelist.add(stage);
	                 
	                }
	            }
	        } catch (IOException e) {
	            throw new DAOException("Error while reading CSV file: " + e.getMessage());
	        } catch (ParseException e) {
				throw new IllegalStateException("Error");  //for the moment
			}
		
		  return stagelist;
	}
    
    
}

