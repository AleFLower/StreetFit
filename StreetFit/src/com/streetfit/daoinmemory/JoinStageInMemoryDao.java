package com.streetfit.daoinmemory;

import java.util.ArrayList;
import java.util.List;

import com.streetfit.dao.JoinStageDao;
import com.streetfit.exception.DAOException;
import com.streetfit.model.Message;
import com.streetfit.model.Participation;

public class JoinStageInMemoryDao implements JoinStageDao {

	private static List <Participation> members = new ArrayList<>();  //se non static, non funziona, ovviamente...(vedi definizione di attributo static)
	private static List<Message> messages = new ArrayList<>();
	@Override
	public void registrateParticipation(Participation p) {
		
		if(p == null) {
			throw new IllegalStateException("Error");
		}	
		members.add(p);
	}

	@Override
	public List<Participation> showMembers() {
		return members;
	}

	 @Override
	    public void depositMessage(Message m) throws DAOException {
	        if (m == null || m.getFromUser() == null || m.getContent() == null) {
	            throw new DAOException("Invalid message data.");
	        }

	        // Aggiungi il messaggio alla lista in memoria
	        messages.add(m);
	    }

	@Override
	public List<Message> retrieveMessage() throws DAOException {
		// TODO Auto-generated method stub
		return messages;
	}

	@Override
	public void updateMessage(Message updatedMessage) {
	    for (Message m : messages) {
	        if (m.getFromUser().equals(updatedMessage.getFromUser()) &&
	            m.getContent().equals(updatedMessage.getContent())) {
	            m.setReply(updatedMessage.getReply());
	            return;
	        }
	    }
	    throw new IllegalStateException("Message not found to update");
	}


}
