package main.java.com.streetfit.controller;

import java.util.ArrayList;
import java.util.List;

import main.java.com.streetfit.dao.FactorySingletonDAO;
import main.java.com.streetfit.dao.JoinStageDao;
import main.java.com.streetfit.exception.DAOException;
import main.java.com.streetfit.model.Message;
import main.java.com.streetfit.model.Participation;
import main.java.com.streetfit.model.TrainerNotification;
import main.java.com.streetfit.model.TrainingStage;
import main.java.com.streetfit.utils.NotificationQueue;

public class JoinStageController {
	
	//valutare poi se mettere chain of responsibility qui
	
	private JoinStageDao dao;
	
	public JoinStageController() {
		try {
		this.dao = FactorySingletonDAO.getDefaultDAO().getJoinStageDao();
	       }
		catch(RuntimeException e) {
			throw new IllegalStateException("Failed to initialize  due to DAO error", e);
		     }
      }
	
	public void registrateMember(Participation p, Message m) {
		if(dao == null) {
			throw new IllegalStateException("Error");
		}
		
		try {
			dao.registrateParticipation(p);
			 String msg = "🔔 " + p.getUsername() + " join the stage : " + p.getStage();
		     NotificationQueue.getInstance().addNotification(new TrainerNotification(msg));
		     
		     //have to send message to the trainer, if present
		     if(m!=null) {
		    	 dao.depositMessage(m);
		     }
		}
		catch(DAOException e) {
		
			throw new IllegalStateException("Dao Error");
		}
	}
	
	public List<Message> retrieveMessages(){
		List<Message> messages;
		if(dao == null) {
			throw new IllegalStateException("Error");
		}
		
		try {
			messages = dao.retrieveMessage();
		}
		catch(DAOException e) {
			
			throw new IllegalStateException("Failed to retrieve messages", e);
		}
		
		return messages;
	}
	
	public void updateMessage(Message m, String reply) {
		
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
	
	public List<Participation> showMembers() {
		
		List <Participation> members;
		if(dao == null) {
			throw new IllegalStateException("Error");
		}
		
		try {
			members = dao.showMembers();
		}
		catch(RuntimeException e) {
			
			throw new IllegalStateException("Failed to retrieve members", e);
		}
		
		return members;
	}
	 
	
	//here you can calculate the amount of subscribers to the stage and show the remaining tickets, then check if there are any
	//available tickets, if not, show sold out
	
	public List<Integer> getSubscribers(List<TrainingStage> stageList) {
		
		//dovremo fare una sorta di join a livello di codice java, non sql, scorro prima lista, poi seconda, confronto i titoli e se si tengo da parte una somma dei tickets e poi sottraggo
	
		List <Participation> members = showMembers();
		List<Integer> counters = new ArrayList<>();

		
		for(TrainingStage stage : stageList) {
			int counter = 0;  // Reset qui!
			int remaining=0;
			
			for(Participation p:members) {
				if (stage.getTitle().equals(p.getStage())) {
					int tickets = p.getTicket();
					counter += tickets;
				}
			}
			remaining = stage.getMaxParticipants() - counter;
			counters.add(remaining);
		}
		
		return counters;
	}
	
	
	public double getTotalOutcome() {
		List <Participation> members = showMembers();
		double total = 0;
		
		for(Participation member:members) {
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
}
