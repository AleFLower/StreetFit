package com.streetfit.controllercli;

import java.util.List;

import com.streetfit.beans.HealthFormBean;
import com.streetfit.beans.TicketBean;
import com.streetfit.controller.AddStageController;
import com.streetfit.controller.JoinStageController;
import com.streetfit.decorator.BasicTicket;
import com.streetfit.decorator.SpecialTicket;
import com.streetfit.decorator.Ticket;
import com.streetfit.decorator.VipTicket;
import com.streetfit.model.Credentials;
import com.streetfit.model.HealthForm;
import com.streetfit.model.Participation;
import com.streetfit.model.TrainingStage;
import com.streetfit.strategy.PromotionalEvent;
import com.streetfit.strategy.StandardTicket;
import com.streetfit.strategy.TicketStrategy;
import com.streetfit.strategy.VipStrategy;
import com.streetfit.viewcli.DashboardParticipantCLI;
import com.streetfit.chainofresponsibility.*;

public class ParticipantControllerCLI{   
	
	private DashboardParticipantCLI view = new DashboardParticipantCLI();
	private JoinStageController joinstagecontroller = new JoinStageController();
	

   public void start(Credentials cred) {
  
   HomeGUIControllerCLI controller = new HomeGUIControllerCLI();
	int choice;
	while(true) {
	choice = view.showMenu();
	
	switch(choice) {
	
	case 1: joinStage(cred);
	break;
	case 4:
		controller.start();
	break;
	default: System.exit(1); //for the moment...
	}
	}
	
	} 
	
	public void joinStage(Credentials cred) {
		
		AddStageController controller = new AddStageController();
		int choice = view.printAllStages(controller.getAllStages(),joinstagecontroller.getSubscribers(controller.getAllStages()));
		TrainingStage chosenstage = null;
		double total=0;
		
		if(choice >= 0 && choice < controller.getAllStages().size()) {
			  chosenstage = controller.getAllStages().get(choice);
			  
		}
	   try {
		   
		   HealthFormBean beanform = view.fillHealthForm();
		   HealthForm form = new HealthForm(beanform.hasInjuries(), beanform.hasHeartIssues(), beanform.isPhysicallyFit());
		   
		   //create handler with polimorphysm
		   //per il momento, per testare chain of resp. la metto qui, ma forse va messa nel controller generale perche mi serve anche per la FX
		      
	        HealthCheckHandler heartCheck = new HeartCheckHandler();
	        HealthCheckHandler injuryCheck  = new InjuriesCheckHandler();
	        HealthCheckHandler isPhysicallyCheck = new FitnessCheckHandler();
	        
	        //let's concatenate all
	        injuryCheck.setNext(heartCheck);
	        heartCheck.setNext(isPhysicallyCheck);
	        
	        if(injuryCheck.handle(form)) {  //solo se passi tutti i controlli allora potrai acquistare il biglietto
		   
		   TicketBean ticketBean = view.selectTicket();
	       Ticket ticket;
	       
	       TicketStrategy strategy;

	       
	       //qua in mezzo metti pattern com.streetfit.strategy
	        switch (ticketBean.getTicketType()) {
	            case "base" -> {
	            	ticket = new BasicTicket(ticketBean.getQuantity());
	            	strategy = new StandardTicket();
	            	total = strategy.applyEvents(ticket);
	            }
	        
	            case "special" -> {
	            	ticket = new SpecialTicket(new BasicTicket(ticketBean.getQuantity()));
	            	strategy = new PromotionalEvent();
	            	total = strategy.applyEvents(ticket);
	            }
	            case "vip" -> {
	            ticket = new VipTicket(new BasicTicket(ticketBean.getQuantity()));
	            strategy = new VipStrategy();
            	total = strategy.applyEvents(ticket);
	            }
	            default -> {
	                view.printMessage("Invalid ticket type.");
	                return;
	            }
	        }
	    
	        List <Integer> members = joinstagecontroller.getSubscribers(controller.getAllStages());
	        if(ticket.getQuantity() > members.get(choice)) {
	        	view.printMessage("Cannot purchase the ticket");
	        	return;
	        }
	        
	        
	        view.printTicketSummary(ticket.getDescription(), total);
	       
	        Participation p = new Participation(cred.getUsername(),chosenstage.getTitle(), ticket.getQuantity());
	        
	        joinstagecontroller.registrateMember(p);
	        view.printMessage("Registration successful");	        
	        
	        }
	      
	        
	   }catch(Exception e) {
		   e.printStackTrace();
		   throw new IllegalStateException("Input error while creating health form");
	   }
 
	
	}
}
