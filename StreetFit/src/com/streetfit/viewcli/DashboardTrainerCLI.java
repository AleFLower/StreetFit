package com.streetfit.viewcli;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import com.streetfit.beans.StageBean;
import com.streetfit.model.Message;
import com.streetfit.model.Participation;
import com.streetfit.model.TrainingStage;

public class DashboardTrainerCLI {
	
	private Scanner sc = new Scanner(System.in) ;

	public int showMenu() {
		
		int choice;
	
		
		System.out.println("-----Welcome to StreetFit----");  //NOSONAR
		System.out.println("1. Add new stage");//NOSONAR
		//per il momento, poi vedremo che casi d'uso fare, questa non e un caso d'uso
		System.out.println("2. Members");  //NOSONAR
		System.out.println("3. Created stages");//NOSONAR
		System.out.println("4. Q&A");
		System.out.println("5. Logout");//NOSONAR
		System.out.println("Please, enter your choice: ");//NOSONAR
			
		choice = sc.nextInt();
		sc.nextLine(); // importante per evitare salti nell'input dopo
		return choice;
	}
	
	public StageBean addstage() throws IOException{
		 BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		
		System.out.println("Enter the title of the stage: ");//NOSONAR
		String title = reader.readLine();
		System.out.println("Enter the workout itinerary:");//NOSONAR
		String itinerary = reader.readLine();
		
		System.out.println("Enter the category of the stage (Functional, Yoga, Dance, Stretching): ");//NOSONAR
        String category = reader.readLine();

        System.out.println("Enter the date of the stage (format: yyyy-MM-dd): ");//NOSONAR
        String dateString = reader.readLine();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");//NOSONAR
        Date date = null;
        try {
            date = sdf.parse(dateString);
        } catch (Exception e) {
            System.out.println("Invalid date format. Please enter the date in yyyy-MM-dd format.");//NOSONAR
            return null;
        }

        System.out.println("Enter the place of the stage: ");//NOSONAR
        String place = reader.readLine();

        System.out.println("Enter the maximum number of participants: ");//NOSONAR
        int maxParticipants = Integer.parseInt(reader.readLine());

        StageBean stagebean = new StageBean(title, itinerary, category, date, place, maxParticipants) ;
        if(stagebean.isValid()) {
        	return stagebean;
        }
        return null;
	}
	  // Metodo per visualizzare le notifiche
    public void displayNotification(String message) {
        System.out.println(message); //NOSONAR
        try {
            Thread.sleep(2000); // Simula il "toast" per 2 secondi per ogni notifica
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
	
	public void printAllStages(List<TrainingStage> stages) {//ATTENZIONE: DEVE ESSERE UNA LISTA DI BEANS!!
	    System.out.println("=========== Available Stages ===========");//NOSONAR
	    for (TrainingStage stage : stages) {
	        System.out.println("----------------------------------------");//NOSONAR
	        System.out.println(" Title: " + stage.getTitle());//NOSONAR
	        System.out.println(" Itinerary: " + stage.getItinerary());//NOSONAR
	        System.out.println(" Category: " + stage.getCategory());//NOSONAR
	        System.out.println(" Date: " + stage.getDate());//NOSONAR
	        System.out.println(" Location: " + stage.getLocation());//NOSONAR
	        System.out.println(" Max Participants: " + stage.getMaxParticipants());//NOSONAR
	     //   System.out.println("Available tickets: " + );  devo trovare un modo per inserire questo, cosi che mostriamo ogni volta quanti biglietti rimangono
	        System.out.println("----------------------------------------\n");//NOSONAR
	    }
	}
	
	public void printMembers(List<Participation> p) { //ATTENZIONE: DEVE ESSERE UNA LISTA DI BEANS!!
	    System.out.println("=========== Subscribed Members ===========");//NOSONAR

	    if (p.isEmpty()) {
	        System.out.println("[No members enrolled]");//NOSONAR
	        return;
	    }

	    for (Participation member : p) {
	        System.out.println("----------------------------------------");//NOSONAR
	        System.out.println(" Username: " + member.getUsername());//NOSONAR
	        System.out.println(" Stage: " + member.getStage());//NOSONAR
	        System.out.println(" Tickets bought: " + member.getTicket());//NOSONAR
	        System.out.println("----------------------------------------\n");//NOSONAR
	    }
	}
	
	public boolean printMessages(List<Message> messages) {
	    if (messages == null || messages.isEmpty()) {
	        System.out.println("No messages to display."); //NOSONAR
	        return false;
	    }

	    System.out.println("========= Messages =========");//NOSONAR

	    int count = 1;
	    for (Message msg : messages) {
	        System.out.println("Message #" + count++); //NOSONAR
	        System.out.println("From:    " + msg.getFromUser());//NOSONAR
	        System.out.println("Content: " + msg.getContent());//NOSONAR

	        if (msg.hasReply()) {
	            System.out.println("Reply:   " + msg.getReply());//NOSONAR
	        } else {
	            System.out.println("Reply:   [No reply yet]");//NOSONAR
	        }

	        System.out.println("-----------------------------");//NOSONAR
	    }
	    
	    

	    System.out.println("========= End of List =========");//NOSONAR
	    
	    return true;
	}
	
	public int replyToMessages() {
		System.out.println("Select a message you want to reply to(0 to exit): ");//NOSONAR
		int choice = sc.nextInt();
		sc.nextLine(); // <=== Consuma il newline rimasto nel buffer
		return choice;
		
	}
	
	public String askReplyContent() {
	    System.out.print("Enter your reply: ");//NOSONAR
	    String reply = sc.nextLine();
	    return reply;
	}

	
	public void printSubscribers(List<TrainingStage>stageList, List<Integer>counters) {  //ATTENZIONE: DEVE ESSERE UNA LISTA DI BEANS!!
		
		int i = 0;
		if(stageList.isEmpty()) {
			 System.out.println("[No stages enrolled]");//NOSONAR
			 return;
		}
		System.out.println("Created stages: ");//NOSONAR
		for(TrainingStage stage:stageList) {
			if(counters.get(i)==0) System.out.println(stage.getTitle() + "  " + " -> Sold out");//NOSONAR
			else System.out.println(stage.getTitle() + "  " + " -> remaining tickets for this stage: " + counters.get(i));//NOSONAR
			i++;
		}
	}
	  public void printMessage(String message) {
	        System.out.println(message);//NOSONAR
	    }
}
