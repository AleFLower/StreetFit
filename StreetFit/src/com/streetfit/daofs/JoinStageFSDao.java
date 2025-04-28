package com.streetfit.daofs;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.streetfit.dao.JoinStageDao;
import com.streetfit.exception.DAOException;
import com.streetfit.model.Participation;
import com.streetfit.model.TrainingStage;


public class JoinStageFSDao implements JoinStageDao {
	
	  private static final String CSV_FILE = "res/Members.csv";
	
	public void registrateParticipation(Participation p) throws DAOException {
		  
		       String username = p.getUsername();
		       String stage = p.getStage();
		       int tickets = p.getTicket();

		        try (BufferedWriter writer = new BufferedWriter(new FileWriter(CSV_FILE, true))) {
		            // Write the data to CSV file in the format: title, itinerary, category, date, location, intensity, maxParticipants
		            writer.write(String.format("%s,%s,%d%n",username, stage, tickets));
		        } catch (IOException e) {
		            throw new DAOException("Error writing stage data to file: " + e.getMessage(), e);
		        }
		    }

	@Override
	public List<Participation> showMembers() {	
			
			List <Participation> members = new ArrayList<Participation>();
			
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
		                    if (data.length != 3) {   //3 columns expected, like in a database.
		                        continue;
		                    }
		                    String username = data[0].trim();
		                    String stage = data[1].trim();
		                  
		                    int tickets = Integer.parseInt(data[2].trim());
		                    
		                    
		              Participation p = new Participation(username,stage,tickets);
		                 members.add(p);
		                 
		                }
		            }
		        } catch (IOException e) {
		            throw new IllegalStateException("Error while reading CSV file: " + e.getMessage());
		        } 
			
			  return members;
	}
	
	
	
	}

