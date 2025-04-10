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
			System.out.println("1. Add new stage");//NOSONAR
		//per il momento, poi vedremo che casi d'uso fare, questa non è un caso d'uso
			System.out.println("2. View created stage");  
			System.out.println("3. Manage reviews/comment");//NOSONAR
			System.out.println("4. Logout");//NOSONAR
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

        System.out.println("Enter the intensity (low, medium, high): ");//NOSONAR
        String intensity = reader.readLine();

        System.out.println("Enter the maximum number of participants: ");//NOSONAR
        int maxParticipants = Integer.parseInt(reader.readLine());

        StageBean stagebean = new StageBean(title, itinerary, category, date, place, intensity, maxParticipants) ;
        if(stagebean.isValid()) {
        	return stagebean;
        }
        return null;
	}
	
	public void printStageSummary(StageBean stage) {   //just for now, i wish to delete later
		
		System.out.println("Stage inserted correctly");//NOSONAR
		System.out.println("Summary");//NOSONAR
		System.out.println("Stage Title: " + stage.getTitle());//NOSONAR
        System.out.println("Itinerary: " + stage.getItinerary());//NOSONAR
        System.out.println("Category: " + stage.getCategory());//NOSONAR
        System.out.println("Date: " + stage.getDate());//NOSONAR
        System.out.println("Place: " + stage.getPlace());//NOSONAR
        System.out.println("Intensity: " + stage.getIntensity());//NOSONAR
        System.out.println("Max Participants: " + stage.getMaxParticipants());//NOSONAR
	}
}
