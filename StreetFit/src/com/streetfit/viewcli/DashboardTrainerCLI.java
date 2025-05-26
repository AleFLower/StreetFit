package com.streetfit.viewcli;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.streetfit.beans.StageBean;
import com.streetfit.model.Message;
import com.streetfit.model.Participation;
import com.streetfit.model.TrainingStage;

public class DashboardTrainerCLI {
	
	private static final Logger logger = LoggerFactory.getLogger(DashboardTrainerCLI.class);
	private Scanner sc = new Scanner(System.in) ;

	public int showMenu() {
		
		int choice;
		
		logger.info("-----Welcome to StreetFit----");
		logger.info("1. Add new stage");
		//per il momento, poi vedremo che casi d'uso fare, questa non e un caso d'uso
		logger.info("2. Members");
		logger.info("3. Created stages");
		logger.info("4. Q&A");
		logger.info("5. Logout");
		logger.info("Please, enter your choice: ");
			
		choice = sc.nextInt();
		sc.nextLine(); // importante per evitare salti nell'input dopo
		return choice;
	}
	
	public StageBean addstage() throws IOException{
		 BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		
		logger.info("Enter the title of the stage: ");
		String title = reader.readLine();
		logger.info("Enter the workout itinerary:");
		String itinerary = reader.readLine();
		
		logger.info("Enter the category of the stage (Functional, Yoga, Dance, Stretching): ");
        String category = reader.readLine();

        logger.info("Enter the date of the stage (format: yyyy-MM-dd): ");
        String dateString = reader.readLine();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            date = sdf.parse(dateString);
        } catch (Exception e) {
            logger.info("Invalid date format. Please enter the date in yyyy-MM-dd format.");
            return null;
        }

        logger.info("Enter the place of the stage: ");
        String place = reader.readLine();

        logger.info("Enter the maximum number of participants: ");
        int maxParticipants = Integer.parseInt(reader.readLine());

        StageBean stagebean = new StageBean(title, itinerary, category, date, place, maxParticipants);
        if(stagebean.isValid()) {
        	return stagebean;
        }
        return null;
	}
	
    public void displayNotification(String message) {
        logger.info(message);
        try {
            Thread.sleep(2000); // Simula il "toast" per 2 secondi per ogni notifica
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
	
	public void printAllStages(List<TrainingStage> stages) {
	    logger.info("=========== Available Stages ===========");
	    for (TrainingStage stage : stages) {
	        logger.info("----------------------------------------");
	        logger.info(" Title: " + stage.getTitle());
	        logger.info(" Itinerary: " + stage.getItinerary());
	        logger.info(" Category: " + stage.getCategory());
	        logger.info(" Date: " + stage.getDate());
	        logger.info(" Location: " + stage.getLocation());
	        logger.info(" Max Participants: " + stage.getMaxParticipants());
	     //   logger.info("Available tickets: " + );  devo trovare un modo per inserire questo, cosi che mostriamo ogni volta quanti biglietti rimangono
	        logger.info("----------------------------------------\n");
	    }
	}
	
	public void printMembers(List<Participation> p) { 
	    logger.info("=========== Subscribed Members ===========");

	    if (p.isEmpty()) {
	        logger.info("[No members enrolled]");
	        return;
	    }

	    for (Participation member : p) {
	        logger.info("----------------------------------------");
	        logger.info(" Username: " + member.getUsername());
	        logger.info(" Stage: " + member.getStage());
	        logger.info(" Tickets bought: " + member.getTicket());
	        logger.info("----------------------------------------\n");
	    }
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
	        logger.info("From:    " + msg.getFromUser());
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
	
	public int replyToMessages() {
		logger.info("Select a message you want to reply to(0 to exit): ");
		int choice = sc.nextInt();
		sc.nextLine(); // <=== Consuma il newline rimasto nel buffer
		return choice;
	}
	
	public String askReplyContent() {
	    logger.info("Enter your reply: ");
	    return sc.nextLine();
	}

	
	public void printSubscribers(List<TrainingStage> stageList, List<Integer> counters) { 
		
		int i = 0;
		if(stageList.isEmpty()) {
			logger.info("[No stages enrolled]");
			return;
		}
		logger.info("Created stages: ");
		for(TrainingStage stage : stageList) {
			if(counters.get(i) == 0) 
				logger.info(stage.getTitle() + "  " + " -> Sold out");
			else 
				logger.info(stage.getTitle() + "  " + " -> remaining tickets for this stage: " + counters.get(i));
			i++;
		}
	}
	
    public void printMessage(String message) {
        logger.info(message);
    }
}

