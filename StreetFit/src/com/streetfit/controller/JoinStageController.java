package com.streetfit.controller;

import java.util.ArrayList;
import java.util.List;

import com.streetfit.dao.FactorySingletonDAO;
import com.streetfit.dao.JoinStageDao;
import com.streetfit.exception.DAOException;
import com.streetfit.model.Participation;
import com.streetfit.model.TrainingStage;

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
	
	public void registrateMember(Participation p) {
		if(dao == null) {
			throw new IllegalStateException("Error");
		}
		
		try {
			dao.registrateParticipation(p);
		}
		catch(DAOException e) {
			e.printStackTrace();
			throw new IllegalStateException("Dao Error");
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
			e.printStackTrace();
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
}
