package main.java.com.streetfit.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import main.java.com.streetfit.beans.HealthFormBean;
import main.java.com.streetfit.beans.MessageBean;
import main.java.com.streetfit.beans.ParticipationBean;
import main.java.com.streetfit.beans.TicketBean;
import main.java.com.streetfit.beans.TrainingStageBean;
import main.java.com.streetfit.chainofresponsibility.FitnessCheckHandler;
import main.java.com.streetfit.chainofresponsibility.HealthCheckHandler;
import main.java.com.streetfit.chainofresponsibility.HeartCheckHandler;
import main.java.com.streetfit.chainofresponsibility.InjuriesCheckHandler;
import main.java.com.streetfit.dao.DAOFactory;
import main.java.com.streetfit.dao.JoinStageDao;
import main.java.com.streetfit.decorator.BasicTicket;
import main.java.com.streetfit.decorator.SpecialTicket;
import main.java.com.streetfit.decorator.Ticket;
import main.java.com.streetfit.decorator.VipTicket;
import main.java.com.streetfit.exception.DAOException;
import main.java.com.streetfit.model.HealthForm;
import main.java.com.streetfit.model.Message;
import main.java.com.streetfit.model.Participation;
import main.java.com.streetfit.model.TrainerNotification;
import main.java.com.streetfit.strategy.PromotionalEvent;
import main.java.com.streetfit.strategy.StandardTicket;
import main.java.com.streetfit.strategy.TicketStrategy;
import main.java.com.streetfit.strategy.VipStrategy;
import main.java.com.streetfit.utils.NotificationQueue;

public class JoinStageController {
	
	
	private JoinStageDao dao;
	
	
	public JoinStageController() {
		try {
		this.dao = DAOFactory.getDefaultDAO().getJoinStageDao();
	       }
		catch(RuntimeException e) {
			throw new IllegalStateException("Failed to initialize  due to DAO error", e);
		     }
      }
	
	
	
	public void registrateMember(ParticipationBean pbean, MessageBean mbean,NotificationQueue queue) {
		
		Participation p = new Participation(pbean.getUsername(),pbean.getStage(),pbean.getTicket(),pbean.getTotal());
		Message m;
		
		if(mbean!=null) {
		m = new Message(mbean.getFromUser(),mbean.getContent());
		}
		else m = null;
		
		if(dao == null) {
			throw new IllegalStateException("Error");
		}
		
		try {
			dao.registrateParticipation(p);
			 String msg = "🔔 " + p.getUsername() + " join the stage : " + p.getStage().getTitle();
		     queue.addNotification(new TrainerNotification(msg));
		     
		     //have to send message to the trainer, if present
		     if(m!=null) {
		    	 dao.depositMessage(m);
		     }
		}
		catch(DAOException e) {
		
			throw new IllegalStateException("Dao Error");
		}
	}
	
	public boolean isHealthFormValid(HealthFormBean formBean) {
		
		HealthForm form = new HealthForm(formBean.hasHeartIssues(),formBean.hasInjuries(), formBean.isPhysicallyFit());
        HealthCheckHandler heartCheck = new HeartCheckHandler();
        HealthCheckHandler injuryCheck = new InjuriesCheckHandler();
        HealthCheckHandler isPhysicallyCheck = new FitnessCheckHandler();

        injuryCheck.setNext(heartCheck);
        heartCheck.setNext(isPhysicallyCheck);

        return injuryCheck.handle(form);
    }

	public double calculateTicketPrice(TicketBean ticketBean) {
	    String type = ticketBean.getTicketType();
	    int quantity = ticketBean.getQuantity();

	    Ticket ticket;

	    switch (type) {
	        case "basic":
	            ticket = new BasicTicket(quantity);
	            break;
	        case "special":
	            ticket = new SpecialTicket(new BasicTicket(quantity));
	            break;
	        case "vip":
	            ticket = new VipTicket(new BasicTicket(quantity));
	            break;
	        default:
	            throw new IllegalArgumentException("Invalid ticket type");
	    }

	    TicketStrategy strategy;

	    if (ticket instanceof VipTicket) {
	        strategy = new VipStrategy();
	    } else if (ticket instanceof SpecialTicket) {
	        strategy = new PromotionalEvent();
	    } else {
	        strategy = new StandardTicket();
	    }

	    return strategy.applyEvents(ticket);
	}

	
    public List<MessageBean> retrieveMessages() {
        if (dao == null) {
            throw new IllegalStateException("Error");
        }

        try {
            List<Message> messages = dao.retrieveMessage();
            List<MessageBean> messageBeans = new ArrayList<>();

            for (Message msg : messages) {
                MessageBean bean = new MessageBean(msg.getFromUser(),msg.getContent(),msg.getReply());
                messageBeans.add(bean);
            }

            return messageBeans;

        } catch (DAOException e) {
            throw new IllegalStateException("Failed to retrieve messages", e);
        }
    }

	public void updateMessage(MessageBean mbean, String reply) {
		
		Message m = new Message(mbean.getFromUser(),mbean.getContent());
		
		if(dao == null) {
			throw new IllegalStateException("Error");
		}
		try {
			m.setReply(reply);
			dao.updateMessage(m);
		}
         catch(DAOException e) {
			
			throw new IllegalStateException("Failed to retrieve messages", e);
		}
	}
	
	public List<ParticipationBean> retrieveMembers() {

	    List<Participation> members;
	    if (dao == null) {
	        throw new IllegalStateException("Error");
	    }

	    try {
	        members = dao.showMembers();
	    } catch (RuntimeException e) {
	        throw new IllegalStateException("Failed to retrieve members", e);
	    }

	    List<ParticipationBean> beans = new ArrayList<>();
	    for (Participation p : members) {
	        ParticipationBean bean = new ParticipationBean(
	            p.getUsername(),
	            p.getStage(),    
	            p.getTicket(),
	            p.getTotal()
	        );
	        beans.add(bean);
	    }

	    return beans;
	}

	
	//here you can calculate the amount of subscribers to the stage and show the remaining tickets, then check if there are any
	//available tickets, if not, show sold out
	
	public List<Integer> getSubscribers(List<TrainingStageBean> stageBeanList) {
        List<ParticipationBean> members = retrieveMembers();
        List<Integer> counters = new ArrayList<>();

        for (TrainingStageBean stageBean : stageBeanList) {
            // Conversione direttamente qui da TrainingStageBean a TrainingStage
            TrainingStageBean stage = findStageByTitle(stageBean.getTitle());
            int counter = 0;

            for (ParticipationBean p : members) {
                if (stage.getTitle().equals(p.getStage().getTitle())) {
                    counter += p.getTicket();
                }
            }

            int remaining = stage.getMaxParticipants() - counter;
            counters.add(remaining);
        }

        return counters;
    }
	
	public double getTotalOutcome() {
		List <ParticipationBean> members = retrieveMembers();
		double total = 0;
		
		for(ParticipationBean member:members) {
				total += member.getTotal();
		}
		
		return total;
	}
	public void removeParticipation(String username, String stage) {
	    try {
	        dao.removeParticipation(username, stage);
	    } catch (DAOException e) {
	        throw new IllegalStateException("Failed to remove participation", e);
	    }
	}
	
	 public List<TrainingStageBean> getStagesForUser(String username) {
	        List<ParticipationBean> members = retrieveMembers();
	        List<TrainingStageBean> userStages = new ArrayList<>();

	        for (ParticipationBean participation : members) {
	            if (participation.getUsername().equals(username)) {
	                TrainingStageBean stage = findStageByTitle(participation.getStage().getTitle());
	                if (stage != null) {
	                    // Conversione direttamente qui da TrainingStage a TrainingStageBean
	                    userStages.add(stage);
	                }
	            }
	        }

	        return userStages;
	    }

	// Metodo che restituisce la stage in base al titolo, qui presupponiamo che la stage abbia un metodo getTitle
	public TrainingStageBean findStageByTitle(String title) {
	   AddStageController controller = new AddStageController();
	   
	    List<TrainingStageBean> allStages = controller.getAllStages();
	    for (TrainingStageBean stage : allStages) {
	        if (stage.getTitle().equals(title)) {
	            return stage;
	        }
	    }
	    return null; // Se non trova una stage corrispondente
	}
	public List<TrainingStageBean> getUniqueStagesForUser(String username) {
	    List<ParticipationBean> allParts = retrieveMembers();
	    List<TrainingStageBean> uniqueStages = new ArrayList<>();
	    Map<String, Integer> stageTicketCounts = new HashMap<>();

	    for (ParticipationBean p : allParts) {
	        if (p.getUsername().equals(username)) {
	            String stageTitle = p.getStage().getTitle();
	            stageTicketCounts.computeIfAbsent(stageTitle, key -> {
	                // Ottieni il TrainingStage completo usando il titolo
	                TrainingStageBean stage = findStageByTitle(stageTitle);
	                if (stage != null) {
	                    // Crea un nuovo TrainingStageBean con i dettagli completi
	                    uniqueStages.add(stage);
	                }
	                return 0;
	            });
	            stageTicketCounts.put(stageTitle, stageTicketCounts.get(stageTitle) + p.getTicket());
	        }
	    }

	    return uniqueStages;
	}



	 public List<Integer> getTicketsBoughtForUniqueStages(String username, List<TrainingStageBean> uniqueStages) {
	        List<ParticipationBean> allParts = retrieveMembers();
	        List<Integer> ticketCounts = new ArrayList<>();

	        for (TrainingStageBean stageBean : uniqueStages) {
	            int totalTickets = 0;
	            TrainingStageBean stage = findStageByTitle(stageBean.getTitle());
	            for (ParticipationBean p : allParts) {
	                if (p.getUsername().equals(username) && p.getStage().equals(stage)) {
	                    totalTickets += p.getTicket();
	                }
	            }
	            ticketCounts.add(totalTickets);
	        }

	        return ticketCounts;
	    }


}
