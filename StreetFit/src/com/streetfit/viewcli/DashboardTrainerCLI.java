package com.streetfit.viewcli;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

import com.streetfit.beans.StageBean;

public class DashboardTrainerCLI {

	public int showMenu() {
		
		int choice;
		
			Scanner sc = new Scanner(System.in) ;
			System.out.println("-----Welcome to StreetFit----");  //NOSONAR
			System.out.println("1. Add new stage");
			System.out.println("2. View created stage");  //per il momento, poi vedremo che casi d'uso fare, questa non è un caso d'uso
			System.out.println("3. Manage reviews/comment");
			System.out.println("4. Logout");
			System.out.println("Please, enter your choice: ");
			
			choice = sc.nextInt();
			sc.nextLine(); // importante per evitare salti nell'input dopo
			return choice;
		
		
	}
	
	public StageBean addstage() throws IOException{
		 BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		
		System.out.println("Enter the title of the stage: ");
		String title = reader.readLine();
		System.out.println("Enter the workout itinerary:");
		String itinerary = reader.readLine();
		
		System.out.println("Enter the category of the stage (Functional, Yoga, Dance, Stretching): ");
        String category = reader.readLine();

        System.out.println("Enter the date of the stage (format: yyyy-MM-dd): ");
        String dateString = reader.readLine();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            date = sdf.parse(dateString);
        } catch (Exception e) {
            System.out.println("Invalid date format. Please enter the date in yyyy-MM-dd format.");
            return null;
        }

        System.out.println("Enter the place of the stage: ");
        String place = reader.readLine();

        System.out.println("Enter the intensity (low, medium, high): ");
        String intensity = reader.readLine();

        System.out.println("Enter the maximum number of participants: ");
        int maxParticipants = Integer.parseInt(reader.readLine());

        StageBean stagebean = new StageBean(title, itinerary, category, date, place, intensity, maxParticipants) ;
        if(stagebean.isValid()) {
        	return stagebean;
        }
        return null;
	}
	
	public void printStageSummary(StageBean stage) {   //just for now, i wish to delete later
		
		System.out.println("Stage inserted correctly");
		System.out.println("Summary");
		System.out.println("Stage Title: " + stage.getTitle());
        System.out.println("Itinerary: " + stage.getItinerary());
        System.out.println("Category: " + stage.getCategory());
        System.out.println("Date: " + stage.getDate());
        System.out.println("Place: " + stage.getPlace());
        System.out.println("Intensity: " + stage.getIntensity());
        System.out.println("Max Participants: " + stage.getMaxParticipants());
	}
}
