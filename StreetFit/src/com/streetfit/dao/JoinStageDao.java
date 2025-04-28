package com.streetfit.dao;

import java.util.List;

import com.streetfit.exception.DAOException;
import com.streetfit.model.Participation;

public interface JoinStageDao {

	void registrateParticipation(Participation p) throws DAOException;
	List<Participation> showMembers();
}
