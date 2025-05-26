package com.streetfit.dao;

import java.util.List;

import com.streetfit.exception.DAOException;
import com.streetfit.model.Message;
import com.streetfit.model.Participation;

public interface JoinStageDao {

	void registrateParticipation(Participation p) throws DAOException;
	List<Participation> showMembers();
	void depositMessage(Message m) throws DAOException;
	List<Message> retrieveMessage()throws DAOException;
	void updateMessage(Message updatedMessage) throws DAOException;
	void removeParticipation(String username, String stage) throws DAOException;  
}
