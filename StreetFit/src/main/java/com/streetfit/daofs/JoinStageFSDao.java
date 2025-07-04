package main.java.com.streetfit.daofs;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import java.util.List;
import java.util.Locale;

import main.java.com.streetfit.beans.TrainingStageBean;
import main.java.com.streetfit.controller.JoinStageController;
import main.java.com.streetfit.dao.JoinStageDao;
import main.java.com.streetfit.exception.DAOException;
import main.java.com.streetfit.model.Message;
import main.java.com.streetfit.model.Participation;
import main.java.com.streetfit.model.TrainingStage;



public class JoinStageFSDao implements JoinStageDao {
	
	  private static final String CSV_FILE = "res/Members.csv";
	  private static final String CSVMESS_FILE = "res/Messages.csv";
	
	public void registrateParticipation(Participation p) throws DAOException {
		
		File file = new File(CSV_FILE);
		  
		       String username = p.getUsername();
		       String stage = p.getStage().getTitle();
		       int tickets = p.getTicket();
		       double total = p.getTotal();

		        try (BufferedWriter writer = new BufferedWriter(new FileWriter(CSV_FILE, true))) {
		        	  if (file.length() == 0) {
		                  writer.write("Username,Stage,Tickets,Total\n");  // Scrivi l'intestazione solo una volta, come per DB
		              }
		        	// Write the data to CSV file in the format: title, itinerary, category, date, location, intensity, maxParticipants
		        	  writer.write(String.format(Locale.US, "%s,%s,%d,%.2f%n", username, stage, tickets, total));
		        } catch (IOException e) {
		            throw new DAOException("Error writing stage data to file: " + e.getMessage(), e);
		        }
		    }

	@Override
	public List<Participation> showMembers() {	
			
			List <Participation> members = new ArrayList<>();
			JoinStageController controller = new JoinStageController();
	
		
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
		                    if (data.length != 4) {   //3 columns expected, like in a database.
		                        continue;
		                    }
		                    String username = data[0].trim();
		                    String stageTitle = data[1].trim();
		                    int tickets = Integer.parseInt(data[2].trim());
		                    float total = Float.parseFloat(data[3].trim());
		                    
		                    TrainingStageBean stage = controller.findStageByTitle(stageTitle);
		                   
		                    TrainingStage trainingStage = new TrainingStage(stage.getTitle(),stage.getItinerary(),stage.getCategory(),stage.getDate(),stage.getPlace(),stage.getMaxParticipants());
		              Participation p = new Participation(username,trainingStage,tickets,total);
		                 members.add(p);
		                 
		                }
		            }
		        } catch (IOException e) {
		            throw new IllegalStateException("Error while reading CSV file: " + e.getMessage());
		        } 
			
			  return members;
	}

	@Override
	public void depositMessage(Message m) throws DAOException {
		
	    // Prepara i dati da scrivere nel file (colonne: fromUser, content, reply)
	    String messageData = String.format("%s,%s,%s%n", 
	                                      m.getFromUser(),   // fromUser
	                                      m.getContent(),    // content
	                                      m.getReply() != null ? m.getReply() : ""); // reply (if any, else empty string)

	    try (BufferedWriter writer = new BufferedWriter(new FileWriter(CSVMESS_FILE, true))) {
	        // Scrivi il messaggio nel file CSV
	        writer.write(messageData);  // Aggiungi una nuova riga con i dati del messaggio
	    } catch (IOException e) {
	        // Gestione eccezione in caso di errore nel salvataggio
	        throw new DAOException("Error writing message data to file: " + e.getMessage(), e);
	    }
	}

	@Override
	public List<Message> retrieveMessage() throws DAOException {
	    List<Message> messages = new ArrayList<>();
	    
	    try (BufferedReader br = new BufferedReader(new FileReader(CSVMESS_FILE))) {
	        String line;
	        boolean headerSkipped = false;
	        
	        while ((line = br.readLine()) != null) {
	            // Skip the header row only once
	            if (headerSkipped) {
	                if (!line.trim().isEmpty()) {
	                    String[] data = line.split(",", -1); // Use -1 to preserve empty fields like reply
	                    if (data.length >= 2) {
	                        messages.add(createMessage(data));
	                    }
	                }
	            } else {
	                headerSkipped = true; // Skip header
	            }
	        }
	    } catch (IOException e) {
	        throw new DAOException("Error reading message data from file: " + e.getMessage(), e);
	    }
	    return messages;
	}

	// Helper function to create a Message from the CSV data
	private Message createMessage(String[] data) {
	    String fromUser = data[0].trim();
	    String content = data[1].trim();
	    String reply = (data.length > 2) ? data[2].trim() : "";
	    return new Message(fromUser, content, reply);
	}




	
	@Override
	public void updateMessage(Message updatedMessage) throws DAOException {
	    List<Message> allMessages = new ArrayList<>();

	    // Step 1: Leggi tutti i messaggi
	    try (BufferedReader reader = new BufferedReader(new FileReader(CSVMESS_FILE))) {
	        String line;
	        while ((line = reader.readLine()) != null) {
	            String[] parts = line.split(",", -1); // -1 to keep empty strings
	            if (parts.length >= 3) {
	                Message m = new Message(parts[0], parts[1]);
	                m.setReply(parts[2]);
	                allMessages.add(m);
	            }
	        }
	    } catch (IOException e) {
	        throw new DAOException("Error reading message file: " + e.getMessage(), e);
	    }

	    // Step 2: Trova il messaggio da aggiornare
	    boolean found = false;
	    for (Message m : allMessages) {
	        if (m.getFromUser().equals(updatedMessage.getFromUser()) &&
	            m.getContent().equals(updatedMessage.getContent())) {
	            m.setReply(updatedMessage.getReply());
	            found = true;
	            break;
	        }
	    }

	    if (!found) {
	        throw new DAOException("Message not found for update.");
	    }

	    // Step 3: Sovrascrivi il file con i dati aggiornati
	    try (BufferedWriter writer = new BufferedWriter(new FileWriter(CSVMESS_FILE))) {
	        for (Message m : allMessages) {
	            writer.write(String.format("%s,%s,%s%n",
	                    m.getFromUser(),
	                    m.getContent(),
	                    m.getReply() != null ? m.getReply() : ""));
	        }
	    } catch (IOException e) {
	        throw new DAOException("Error writing updated messages to file: " + e.getMessage(), e);
	    }
	}
	
	@Override
	public void removeParticipation(String username, String stage) throws DAOException {
	    List<Participation> allParticipations = showMembers(); // carica da CSV
	    boolean removed = allParticipations.removeIf(p ->
	            p.getUsername().equals(username) && p.getStage().equals(stage));

	    if (!removed) {
	        throw new DAOException("Participation not found for user " + username + " in stage " + stage);
	    }

	    // Sovrascrivi il file
	    try (BufferedWriter writer = new BufferedWriter(new FileWriter(CSV_FILE, false))) {
	        for (Participation p : allParticipations) {
	            writer.write(String.format("%s,%s,%d,%.2f%n",
	                    p.getUsername(),
	                    p.getStage(),
	                    p.getTicket(),
	                    p.getTotal()));
	        }
	    } catch (IOException e) {
	        throw new DAOException("Error writing to participation file", e);
	    }
	}




	}
	
	

