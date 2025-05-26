package com.streetfit.controllercli;

import java.util.ArrayList;
import java.util.List;

import com.streetfit.beans.HealthFormBean;
import com.streetfit.beans.TicketBean;
import com.streetfit.controller.AddStageController;
import com.streetfit.controller.JoinStageController;
import com.streetfit.decorator.BasicTicket;
import com.streetfit.decorator.SpecialTicket;
import com.streetfit.decorator.Ticket;
import com.streetfit.decorator.VipTicket;
import com.streetfit.exception.InvalidStageChoiceException;
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
import com.streetfit.model.Message;

public class ParticipantControllerCLI{   
	
	private DashboardParticipantCLI view = new DashboardParticipantCLI();
	private JoinStageController joinstagecontroller = new JoinStageController();
	private Credentials cred;
	
	public ParticipantControllerCLI(Credentials cred) {
		this.cred = cred;
	}
	

   public void start() {
  
	  
	int choice;
	boolean running = true; // Introduce a flag

    while (running) {
        choice = view.showMenu();

        switch (choice) {
            case 1:
                joinStage();
                break;
            case 2:
            	handleMessage();
            	break;
            case 3:
             return;
            case 4:
                searchStage();
                break;
                
            case 0: // user selects 0 to exit
               
                running = false; // stop the loop safely
                break;
            default:
              throw new IllegalStateException("ciao"); //for the moment
                // No System.exit() immediately — let user try again
        }
    }
	
	} 
	
   public void joinStage() {
	    AddStageController controller = new AddStageController();
	    List<TrainingStage> stages = controller.getAllStages();
	    int choice = view.printAllStages(stages, joinstagecontroller.getSubscribers(stages));
	    double total = 0;
	    TrainingStage chosenStage;

	    try {
	        if (choice < 0 || choice >= stages.size()) {
	            throw new InvalidStageChoiceException(choice, stages.size());
	        }

	        chosenStage = stages.get(choice);

	        HealthFormBean beanform = view.fillHealthForm();
	        HealthForm form = new HealthForm(beanform.hasInjuries(), beanform.hasHeartIssues(), beanform.isPhysicallyFit());

	        // Chain of Responsibility
	        HealthCheckHandler heartCheck = new HeartCheckHandler();
	        HealthCheckHandler injuryCheck = new InjuriesCheckHandler();
	        HealthCheckHandler isPhysicallyCheck = new FitnessCheckHandler();

	        injuryCheck.setNext(heartCheck);
	        heartCheck.setNext(isPhysicallyCheck);

	        if (injuryCheck.handle(form)) {
	            TicketBean ticketBean = view.selectTicket();
	            Ticket ticket;
	            TicketStrategy strategy;

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

	            List<Integer> members = joinstagecontroller.getSubscribers(stages);
	            if (ticket.getQuantity() > members.get(choice)) {
	                view.printMessage("Cannot purchase the ticket. Not enough available slots.");
	                return;
	            }

	            view.printTicketSummary(ticket.getDescription(), total);

	            String reply = view.getMessage();
	            Message message = null;
	            if (!reply.isEmpty()) {
	                message = new Message(cred.getUsername(), reply);
	            }

	            Participation p = new Participation(cred.getUsername(), chosenStage.getTitle(), ticket.getQuantity(), total);
	            joinstagecontroller.registrateMember(p, message);

	            view.printMessage("Registration successful!");
	        }

	    } catch (InvalidStageChoiceException e) {
	        // 🎯 Gestione vera e propria, non solo propagazione
	        view.printMessage("Error: " + e.getMessage());
	        view.printMessage("Please enter a valid stage number between 0 and " + (e.getMaxIndex() - 1));
	    } catch (Exception e) {
	        view.printMessage("An unexpected error occurred: " + e.getMessage());
	    }
	}

	
	public void handleMessage() {
		 List<Message> messages = joinstagecontroller.retrieveMessages();
    	 List<Message> userMessage = new ArrayList<>();
    	 
    	 for(Message m : messages) {
    		 if(m.getFromUser().equals(cred.getUsername())) {
    		
    			 userMessage.add(m);
    		 }
    	 }
    	 view.printMessages(userMessage);
    	 
	}
	
	private void searchStage() {
	    List<TrainingStage> stages = new AddStageController().getAllStages();
	    String keyword = view.askSearchKeyword().toLowerCase();
	    List<TrainingStage> results = new ArrayList<>();

	    for (TrainingStage stage : stages) {
	        if (stage.getTitle().toLowerCase().contains(keyword) ||
	            stage.getCategory().toLowerCase().contains(keyword) ||
	            stage.getLocation().toLowerCase().contains(keyword)) {
	            results.add(stage);
	        }
	    }

	    if (results.isEmpty()) {
	        view.printMessage("No stages found matching: " + keyword);
	    } else {
	        view.printAllStages(results, new JoinStageController().getSubscribers(results));
	    }
	}

}
