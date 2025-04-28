package com.streetfit.controllercli;

import java.util.ArrayList;
import java.util.List;

import com.streetfit.beans.StageBean;
import com.streetfit.controller.AddStageController;
import com.streetfit.controller.JoinStageController;
import com.streetfit.model.Participation;
import com.streetfit.model.TrainingStage;
import com.streetfit.viewcli.DashboardTrainerCLI;

public class TrainerControllerCLI {  
	
	private DashboardTrainerCLI view = new DashboardTrainerCLI();  
	
	private JoinStageController joinController= new JoinStageController();
	private AddStageController addStagecontroller = new AddStageController();
	
	
    public void start() {
    	
    	HomeGUIControllerCLI controller = new HomeGUIControllerCLI();
    	 int choice;
  
    	 
   while(true) { 
	 
	  choice = view.showMenu();   //calls the view for user input
	
	    switch(choice) {
	       case 1: addstage();
	       break;
	       case 2:
	    	   members();  //just for now
	    	   break;
	       case 3:
	    	   getNumberOfSub();
	    	   break;
	       case 4:
	    	   controller.start();
	    	   break;
	       default:
		        throw new IllegalArgumentException("Not a valid argument");
	        }
	   }
	
      }


     public void addstage() {  //use case: addStage: build a bean from the view and calls the "general" controller AddStageController
       
       TrainingStage stage;
	   StageBean stagebean;
	   try {
	     stagebean = view.addstage();
	     if(stagebean != null) stage = new TrainingStage(stagebean.getTitle(), stagebean.getItinerary(), stagebean.getCategory(), stagebean.getDate(),stagebean.getPlace(), stagebean.getMaxParticipants());
	     else throw new  IllegalArgumentException("Error on creating StageBean");
	   }
	   catch(Exception e) {  //i have to catch the throws statement from DashboardTrainerCLi method addstage
		   throw new IllegalArgumentException("Error during input of stage information");
		  
	   }
	 
	     //calls the general controller to implement the use case
	   addStagecontroller.addstage(stage);
	   
	   view.printAllStages(addStagecontroller.getAllStages());
	 }
     
     public void members() {
    	 List <Participation> members;
    	 members = joinController.showMembers();
    	 view.printMembers(members);
     }
     
     public void getNumberOfSub() {
    	 //retrieve the stage list
    	 
    	 List <TrainingStage> stageList = addStagecontroller.getAllStages();
    	 List<Integer> counters = joinController.getSubscribers(stageList);
    	 
    	 view.printSubscribers(stageList, counters);
     }
}
