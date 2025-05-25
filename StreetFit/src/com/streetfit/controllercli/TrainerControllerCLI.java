package com.streetfit.controllercli;


import java.util.List;
import java.util.Queue;

import com.streetfit.beans.StageBean;
import com.streetfit.controller.AddStageController;
import com.streetfit.controller.JoinStageController;
import com.streetfit.model.Message;
import com.streetfit.model.Participation;
import com.streetfit.model.TrainerNotification;
import com.streetfit.model.TrainingStage;
import com.streetfit.utils.NotificationQueue;
import com.streetfit.viewcli.DashboardTrainerCLI;

public class TrainerControllerCLI {  
	
	private DashboardTrainerCLI view = new DashboardTrainerCLI();  
	
	private JoinStageController joinController= new JoinStageController();
	private AddStageController addStagecontroller = new AddStageController();
	
	
    public void start() {
    	
    	 int choice;
    	 HomeGUIControllerCLI controller = new HomeGUIControllerCLI();
    	 trainerLogin();
    	 
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
	    	   handleMessages();
	    	   break;
	       case 5:
	    	   return;
	    	   
	       default:
		        throw new IllegalArgumentException("Not a valid argument");
	        }
	   }
	
      }
    
 // Metodo per la gestione delle notifiche al login
    public void trainerLogin() {
        // Ottieni e cancella le notifiche dalla coda
        Queue<TrainerNotification> notifications = NotificationQueue.getInstance().getAndClearNotifications();

        if (notifications.isEmpty()) {
            view.printMessage("📭 No new notifications");
        } else {
        	 view.printMessage("📬 There are new notifications:");
            // Visualizza le notifiche una per una
            for (TrainerNotification n : notifications) {
                view.displayNotification(n.getMessage());  // Passa il messaggio alla view per la visualizzazione
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
     
     
     public void handleMessages() {
    	 JoinStageController controller = new JoinStageController();
    	 
    	 List<Message> messages = controller.retrieveMessages();
    	 boolean areThereMessages=view.printMessages(messages);
    	 
    	 if(!areThereMessages) return;
    	 int choice = view.replyToMessages();
    	// Verifica validità dell'indice
    	 if(choice == 0) return;
    	 if (choice < 0 || choice > messages.size()) {
    	     view.printMessage("Invalid message selection.");
    	     return;
    	 }
    	 
    	// Ottieni il messaggio selezionato
    	 Message selectedMessage = messages.get(choice - 1);
    	 String replyText = view.askReplyContent(); // funzione che legge una stringa da input
    	 
    	 controller.updateMessage(selectedMessage, replyText);
    	 
     }
}
