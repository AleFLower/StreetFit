package main.java.com.streetfit.dao;

import java.util.List;

import main.java.com.streetfit.exception.DAOException;
import main.java.com.streetfit.model.Message;
import main.java.com.streetfit.model.Participation;

public interface JoinStageDao {

	void registrateParticipation(Participation p) throws DAOException;
	List<Participation> showMembers();
	void depositMessage(Message m) throws DAOException;
	List<Message> retrieveMessage()throws DAOException;
	void updateMessage(Message updatedMessage) throws DAOException;
	void removeParticipation(String username, String stage) throws DAOException;  
}
