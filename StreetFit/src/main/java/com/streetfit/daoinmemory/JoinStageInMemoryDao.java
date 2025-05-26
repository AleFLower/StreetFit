package main.java.com.streetfit.daoinmemory;

import java.util.ArrayList;
import java.util.List;

import main.java.com.streetfit.dao.JoinStageDao;
import main.java.com.streetfit.exception.DAOException;
import main.java.com.streetfit.model.Message;
import main.java.com.streetfit.model.Participation;

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
	
	public void removeParticipation(String username, String stage) throws DAOException {
        boolean found = false;
        for (int i = 0; i < members.size(); i++) {
            Participation p = members.get(i);
            if (p.getUsername().equals(username) && p.getStage().equals(stage)) {
                members.remove(i);
                found = true;
                break;
            }
        }

        if (!found) {
            throw new DAOException("Participation not found for user " + username + " in stage " + stage);
        }
    }

}
