package main.java.com.streetfit.controllercli;

import java.util.ArrayList;
import java.util.List;

import main.java.com.streetfit.beans.HealthFormBean;
import main.java.com.streetfit.beans.MessageBean;
import main.java.com.streetfit.beans.ParticipationBean;
import main.java.com.streetfit.beans.TrainingStageBean;
import main.java.com.streetfit.beans.TicketBean;
import main.java.com.streetfit.controller.AddStageController;
import main.java.com.streetfit.controller.JoinStageController;

import main.java.com.streetfit.exception.InvalidStageChoiceException;
import main.java.com.streetfit.model.Credentials;

import main.java.com.streetfit.model.TrainingStage;
import main.java.com.streetfit.utils.NotificationQueue;
import main.java.com.streetfit.viewcli.DashboardParticipantCLI;

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
	    List<TrainingStageBean> userStages = joinstagecontroller.getUniqueStagesForUser(cred.getUsername());
	    List<Integer> remainings = joinstagecontroller.getSubscribers(userStages);
	    List<Integer> bought = joinstagecontroller.getTicketsBoughtForUniqueStages(cred.getUsername(), userStages);

	    if (userStages.isEmpty()) {
	        view.printMessage("You are not joined to any stage.");
	    } else {
	    	
	    	//conversione model -> bean per passarle alla view
	        List<TrainingStageBean> beans = new ArrayList<>();
	        for (TrainingStageBean s : userStages) {
	            beans.add(new TrainingStageBean(
	                s.getTitle(),
	                s.getItinerary(),
	                s.getCategory(),
	                s.getDate(),
	                s.getPlace(),
	                s.getMaxParticipants()
	            ));
	        }

	        view.printStages(beans, remainings, bought);
	    }
	}


   public void joinStage() {
	    AddStageController stageController = new AddStageController();
	    List<TrainingStageBean> stages = stageController.getAllStages();


	    List<Integer> subscribers = joinstagecontroller.getSubscribers(stages);

	    int choice = view.requestAvailableStages(stages, subscribers, subscribers);
	    double total = 0;

	    try {
	        if (choice == -1 || choice < 0 || choice >= stages.size()) {
	            throw new InvalidStageChoiceException(choice, stages.size());
	        }

	        TrainingStageBean chosenStage = stages.get(choice);
	        
	        TrainingStage selectedStage = new TrainingStage(chosenStage.getTitle(),chosenStage.getItinerary(),chosenStage.getCategory(),chosenStage.getDate(),chosenStage.getPlace(),chosenStage.getMaxParticipants());

	        // 🩺 Health check form
	        HealthFormBean beanform = view.fillHealthForm();

	        // ✅ Validazione form
	        boolean isValid = joinstagecontroller.isHealthFormValid(beanform);
	        if (!isValid) return;

	        // 🎟️ Selezione ticket (bean)
	        TicketBean ticketBean = view.selectTicket();

	        // ✅ Controllo disponibilità
	        if (ticketBean.getQuantity() > subscribers.get(choice)) {
	            view.printMessage("Cannot purchase the ticket. Not enough available slots.");
	            return;
	        }

	        // ✅ Calcolo del prezzo delegato al controller (usa solo TicketBean)
	        total = joinstagecontroller.calculateTicketPrice(ticketBean);

	        view.printTicketSummary(ticketBean.getTicketType(), total);

	        // 💬 Messaggio facoltativo
	        String reply = view.composeMessage();
	        MessageBean message = null;
	        if (!reply.isEmpty()) {
	            message = new MessageBean(cred.getUsername(), reply);
	        }

	        // ✅ Partecipazione (bean)
	        ParticipationBean p = new ParticipationBean(
	            cred.getUsername(),
	            selectedStage,
	            ticketBean.getQuantity(),
	            total
	        );

	        // ✅ Registrazione
	        joinstagecontroller.registrateMember(p, message, queue);
	        view.printMessage("✅ Registration successful!");

	    } catch (InvalidStageChoiceException e) {
	        view.printMessage("❌ Error: " + e.getMessage());
	    } catch (Exception e) {
	        view.printMessage("❌ An unexpected error occurred: " + e.getMessage());
	    }
	}


   public void handleMessage() {
	    List<MessageBean> messages = joinstagecontroller.retrieveMessages();
	    List<MessageBean> userMessages = new ArrayList<>();

	    for (MessageBean m : messages) {
	        if (m.getFromUser().equals(cred.getUsername())) {
	            userMessages.add(new MessageBean(
	                m.getFromUser(),
	                m.getContent(),
	                m.hasReply() ? m.getReply() : null
	            ));
	        }
	    }

	    view.requestMessages(userMessages);
	}

	
   private void searchStage() {
	    List<TrainingStageBean> stages = new AddStageController().getAllStages();
	    if (stages.isEmpty()) {
	        view.printMessage("No stage available");
	        return;
	    }

	    String keyword = view.askSearchKeyword().toLowerCase();
	    List<TrainingStageBean> results = new ArrayList<>();

	    for (TrainingStageBean s : stages) {
	        if (s.getTitle().toLowerCase().contains(keyword) ||
	            s.getCategory().toLowerCase().contains(keyword) ||
	            s.getPlace().toLowerCase().contains(keyword)) {
	            results.add(s);
	        }
	    }

	    if (results.isEmpty()) {
	        view.printMessage("No stages found matching: " + keyword);
	        return;
	    }

	    List<Integer> subs = joinstagecontroller.getSubscribers(results);

	    view.requestAvailableStages(results, subs, subs);
	}


}
