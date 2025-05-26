package com.streetfit.viewcli;

import java.util.List;
import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.streetfit.beans.HealthFormBean;
import com.streetfit.beans.TicketBean;
import com.streetfit.model.Message;
import com.streetfit.model.TrainingStage;

public class DashboardParticipantCLI {
	
	 private static final Logger logger = LoggerFactory.getLogger(DashboardParticipantCLI.class);
	private final Scanner sc = new Scanner(System.in);
	
	public int showMenu() {
		
			int choice;

			  logger.info("-----Welcome to StreetFit----");
			   logger.info("1. Join a stage");
			    logger.info("2. Your Q&A");
			    logger.info("3. Logout");
			    logger.info("4. Search stage by keyword");
			    logger.info("Please, enter your choice: ");
			choice = sc.nextInt();
			sc.nextLine(); // importante per evitare salti nell'input dopo
			return choice;
	}
	
	public String askSearchKeyword() {
	   logger.info("Enter keyword (title/category/place): ");
	    return sc.nextLine().trim();
	}
	
	public int printAllStages(List<TrainingStage> stages, List<Integer> list) {
	    int choice;
	    int i = 1;

	    logger.info("\n===========  Available Stages  ===========\n");

	    for (TrainingStage stage : stages) {
	        if (list.get(i - 1) == 0) {   // Skip stages not available
	            i++;
	            continue;
	        }

	        logger.info("Stage " + i + ":");
	        logger.info(" Title: " + stage.getTitle());
	        logger.info(" Itinerary: " + stage.getItinerary());
	        logger.info(" Category: " + stage.getCategory());
	        logger.info(" Date: " + stage.getDate());
	        logger.info(" Location: " + stage.getLocation());
	        logger.info(" Max Participants: " + stage.getMaxParticipants());
	        logger.info("----------------------------------------------\n");
	        i++;
	    }

	    logger.info("Please select a stage by number:");
	    choice = sc.nextInt();
	    choice -= 1; // to match list index

	    return choice;
	}

	
	 public HealthFormBean fillHealthForm() {
	        logger.info("\n--- Fill in your Health Form ---");

	        logger.info("Do you have any injuries? : ");
	        boolean hasInjuries = readYesNo();

	        logger.info("Do you suffer from any heart issues? : ");
	        boolean hasHeartIssues = readYesNo();

	        logger.info("Are you currently on any medication? : ");
	        boolean onMedication = readYesNo();

	      
	        return new HealthFormBean(hasInjuries, hasHeartIssues, onMedication);
	    }

	    private boolean readYesNo() {
	        while (true) {
	            String input = sc.nextLine().trim().toLowerCase();
	            if (input.equals("yes") || input.equals("y")) return true;
	            if (input.equals("no") || input.equals("n")) return false;
	            logger.info("Please answer yes or no: ");
	        }
	    }
	    public TicketBean selectTicket() {
	        logger.info("Choose your ticket type:");
	        logger.info("base - Basic Ticket (10€)");
	        logger.info("special - Includes T-shirt and bag (20€)");
	        logger.info("vip - Includes T-shirt, bag, headphones, and shoes (35€)");
	        logger.info("Ticket type: ");
	        String type = sc.nextLine();

	        logger.info("How many tickets? ");
	        int qty = Integer.parseInt(sc.nextLine());

	        return new TicketBean(type, qty);
	    }
	    
		public boolean printMessages(List<Message> messages) {
		    if (messages == null || messages.isEmpty()) {
		        logger.info("No messages to display.");
		        return false;
		    }

		    logger.info("========= Messages =========");

		    int count = 1;
		    for (Message msg : messages) {
		        logger.info("Message #" + count++);
		        logger.info("Content: " + msg.getContent());

		        if (msg.hasReply()) {
		            logger.info("Reply:   " + msg.getReply());
		        } else {
		            logger.info("Reply:   [No reply yet]");
		        }

		        logger.info("-----------------------------");
		    }
		    
		    logger.info("========= End of List =========");
		    return true;
		}
	    
	    public String getMessage() {
	    	String reply;
	    	
	    	logger.info("Do you want to ask anything to the trainer?");
	    	reply = sc.nextLine();
	    	
	    	return reply;
	    }
	    
	    public void printTicketSummary(String description, double total) {
	        logger.info("--- Ticket Summary ---");
	        logger.info("Ticket: " + description);
	        logger.info("Total: €" + total);
	    }
	    public void printMessage(String message) {
	        logger.info(message);
	    }
	
}

