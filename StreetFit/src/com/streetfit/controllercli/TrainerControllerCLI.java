package com.streetfit.controllercli;

import com.streetfit.beans.StageBean;
import com.streetfit.controller.AddStageController;
import com.streetfit.daojdbc.ConnectionFactory;

import com.streetfit.model.Role;
import com.streetfit.model.Stage;
import com.streetfit.viewcli.DashboardTrainerCLI;

public class TrainerControllerCLI implements Controller{  
	
	private DashboardTrainerCLI view = new DashboardTrainerCLI();
	private Stage stage;
	
	
    public void start() {
    	ConnectionFactory.changeRole(Role.TRAINER);
		
	  int choice;
	  choice = view.showMenu();
	
	 // while(true) {    insert later
	
	    switch(choice) {
	       case 1: addstage();
	       break;
	       case 2:
	    	   System.exit(1);  //just for now
		   break;
	//other future cases
	       default:
		        throw new IllegalArgumentException("Not a valid argument");
	        }
	 //   }
	
      }


     public void addstage() {
	   
	   StageBean stagebean;
	   try {
	     stagebean = view.addstage();
	     if(stagebean != null) stage = new Stage(stagebean.getTitle(), stagebean.getItinerary(), stagebean.getCategory(), stagebean.getDate(),stagebean.getPlace(), stagebean.getIntensity(), stagebean.getMaxParticipants());
	     else throw new  IllegalArgumentException("Error on creating StageBean");
	   }
	   catch(Exception e) {  //i have to catch the throws statement from DashboardTrainerCLi method addstage
		   throw new IllegalArgumentException("Error during input of stage information");
		  
	   }
	 
	   AddStageController controller = new AddStageController();
	   controller.addstage(stage);
	   
	   view.printStageSummary(stagebean);
      }
}
