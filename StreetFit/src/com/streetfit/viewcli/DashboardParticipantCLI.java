package com.streetfit.viewcli;

import java.util.List;
import java.util.Scanner;

import com.streetfit.beans.HealthFormBean;
import com.streetfit.beans.TicketBean;
import com.streetfit.model.TrainingStage;

public class DashboardParticipantCLI {
	
	private final Scanner sc = new Scanner(System.in);
	
	public int showMenu() {
		
			int choice;

		    System.out.println("-----Welcome to StreetFit----");  //NOSONAR
			System.out.println("1. Join a stage");//NOSONAR
			//per il momento, poi vedremo che casi d'uso fare, questa non e un caso d'uso
			System.out.println("2. View available stages");  //NOSONAR
			System.out.println("3. Manage reviews/comment");//NOSONAR
			System.out.println("4. Logout");//NOSONAR
			System.out.println("Please, enter your choice: ");//NOSONAR
				
			choice = sc.nextInt();
			sc.nextLine(); // importante per evitare salti nell'input dopo
			return choice;
	}
	
	public int printAllStages(List<TrainingStage> stages, List<Integer> list) {
	    int choice;
	    int i = 1;

	    System.out.println("\n===========  Available Stages  ===========\n");//NOSONAR

	    for (TrainingStage stage : stages) {
	        if (list.get(i - 1) == 0) {   // Skip stages not available
	            i++;
	            continue;
	        }

	        System.out.println("Stage " + i + ":");//NOSONAR
	        System.out.println(" Title: " + stage.getTitle());//NOSONAR
	        System.out.println(" Itinerary: " + stage.getItinerary());//NOSONAR
	        System.out.println(" Category: " + stage.getCategory());//NOSONAR
	        System.out.println(" Date: " + stage.getDate());//NOSONAR
	        System.out.println(" Location: " + stage.getLocation());//NOSONAR
	        System.out.println(" Max Participants: " + stage.getMaxParticipants());//NOSONAR
	        System.out.println("----------------------------------------------\n");//NOSONAR
	        i++;
	    }

	    System.out.println("Please select a stage by number:");//NOSONAR
	    choice = sc.nextInt();
	    choice -= 1; // to match list index

	    return choice;
	}

	
	 public HealthFormBean fillHealthForm() {
	        System.out.println("\n--- Fill in your Health Form ---");//NOSONAR

	        System.out.print("Do you have any injuries? : ");//NOSONAR
	        boolean hasInjuries = readYesNo();

	        System.out.print("Do you suffer from any heart issues? : ");//NOSONAR
	        boolean hasHeartIssues = readYesNo();

	        System.out.print("Are you currently on any medication? : ");//NOSONAR
	        boolean onMedication = readYesNo();

	      
	        return new HealthFormBean(hasInjuries, hasHeartIssues, onMedication);
	    }

	    private boolean readYesNo() {
	        while (true) {
	            String input = sc.nextLine().trim().toLowerCase();
	            if (input.equals("yes") || input.equals("y")) return true;
	            if (input.equals("no") || input.equals("n")) return false;
	            System.out.print("Please answer yes or no: ");//NOSONAR
	        }
	    }
	    public TicketBean selectTicket() {
	        System.out.println("Choose your ticket type:");//NOSONAR
	        System.out.println("base - Basic Ticket (10€)");//NOSONAR
	        System.out.println("special - Includes T-shirt and bag (20€)");//NOSONAR
	        System.out.println("vip - Includes T-shirt, bag, headphones, and shoes (35€)");//NOSONAR
	        System.out.print("Ticket type: ");//NOSONAR
	        String type = sc.nextLine();

	        System.out.print("How many tickets? ");//NOSONAR
	        int qty = Integer.parseInt(sc.nextLine());

	        return new TicketBean(type, qty);
	    }
	    
	    public void printTicketSummary(String description, double total) {
	        System.out.println("--- Ticket Summary ---");//NOSONAR
	        System.out.println("Ticket: " + description);//NOSONAR
	        System.out.println("Total: €" + total);//NOSONAR
	    }
	    public void printMessage(String message) {
	        System.out.println(message);//NOSONAR
	    }
	
}
