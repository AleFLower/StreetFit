package main.java.com.streetfit.controllercli;

import java.util.ArrayList;
import java.util.List;

import main.java.com.streetfit.beans.HealthFormBean;
import main.java.com.streetfit.beans.TicketBean;
import main.java.com.streetfit.controller.AddStageController;
import main.java.com.streetfit.controller.JoinStageController;
import main.java.com.streetfit.decorator.BasicTicket;
import main.java.com.streetfit.decorator.SpecialTicket;
import main.java.com.streetfit.decorator.Ticket;
import main.java.com.streetfit.decorator.VipTicket;
import main.java.com.streetfit.exception.InvalidStageChoiceException;
import main.java.com.streetfit.model.Credentials;
import main.java.com.streetfit.model.HealthForm;
import main.java.com.streetfit.model.Participation;
import main.java.com.streetfit.model.TrainingStage;
import main.java.com.streetfit.strategy.PromotionalEvent;
import main.java.com.streetfit.strategy.StandardTicket;
import main.java.com.streetfit.strategy.TicketStrategy;
import main.java.com.streetfit.strategy.VipStrategy;
import main.java.com.streetfit.utils.NotificationQueue;
import main.java.com.streetfit.viewcli.DashboardParticipantCLI;
import main.java.com.streetfit.chainofresponsibility.*;
import main.java.com.streetfit.model.Message;

public class ParticipantControllerCLI{   
	
	private DashboardParticipantCLI view = new DashboardParticipantCLI();
	private JoinStageController joinstagecontroller = new JoinStageController();
	private Credentials cred;
	private NotificationQueue queue;
	
	public ParticipantControllerCLI(Credentials cred, NotificationQueue queue) {
		this.cred = cred;
		this.queue = queue;
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
            	  searchStage();
                  break;
            case 4:
              printStages();
              break;
            case 5:
            	return;
                
            case 0: // user selects 0 to exit
               
                running = false; // stop the loop safely
                break;
            default:
              throw new IllegalStateException("ciao"); //for the moment
                // No System.exit() immediately — let user try again
        }
    }
	
	} 
   public void printStages() {
	    // 1) prendi le stage uniche
	    List<TrainingStage> userStages =
	        joinstagecontroller.getUniqueStagesForUser(cred.getUsername());
	    // 2) calcola i ticket rimasti
	    List<Integer> remainings =
	        joinstagecontroller.getSubscribers(userStages);
	    // 3) calcola i ticket comprati
	    List<Integer> bought =
	        joinstagecontroller.getTicketsBoughtForUniqueStages(
	            cred.getUsername(), userStages);

	    if (userStages.isEmpty()) {
	        view.printMessage("You are not joined to any stage.");
	    } else {
	        // 4) passa tutte e tre le liste
	        view.printStages(userStages, remainings, bought);
	    }
	}



public void joinStage() {
	    AddStageController controller = new AddStageController();
	    List<TrainingStage> stages = controller.getAllStages();
	    
	    int choice = view.printAllStages(stages, joinstagecontroller.getSubscribers(stages),joinstagecontroller.getSubscribers(stages));
	    double total = 0;
	    TrainingStage chosenStage;

	    try {
	    	
	    	if(choice == -1) { return;}
	    	
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
	            joinstagecontroller.registrateMember(p, message,queue);

	            view.printMessage("Registration successful!");
	        }

	    } catch (InvalidStageChoiceException e) {
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
	    List<TrainingStage> results = new ArrayList<>();
	    
	    if(stages.isEmpty()) {
	    	view.printMessage("No stage available");
	    	return;
	    }
	    
	    String keyword = view.askSearchKeyword().toLowerCase();

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
	        view.printAllStages(results, joinstagecontroller.getSubscribers(results),null);
	    }
	}

}
