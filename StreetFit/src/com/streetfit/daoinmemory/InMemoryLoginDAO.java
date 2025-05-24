package com.streetfit.daoinmemory;


import java.util.ArrayList;
import java.util.List;

import com.streetfit.dao.LoginDao;
import com.streetfit.exception.DAOException;
import com.streetfit.model.Credentials;
import com.streetfit.model.Role;

public class InMemoryLoginDAO implements LoginDao {

    private final List<Credentials> users;

    public InMemoryLoginDAO() {
    	String roleParticipant = "participant";
        users = new ArrayList<>();
        // Creiamo utenti predefiniti
        users.add(new Credentials("trainer", "trainer", Role.TRAINER));
        users.add(new Credentials("participant", roleParticipant, Role.PARTICIPANT));
        users.add(new Credentials("participant2", roleParticipant, Role.PARTICIPANT));
        users.add(new Credentials("participant3", roleParticipant, Role.PARTICIPANT));
    }

    @Override
    public Credentials getCredentials(String username, String password) throws DAOException {
        for (Credentials user : users) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                return user;
            }
        }
        throw new DAOException("Not valid credentials");
    }

	@Override
	public void signup(Credentials cred) throws DAOException {
		if(cred != null) {
			for(Credentials credentials:users) {
				if(credentials.getUsername().equals(cred.getUsername())) {
					throw new IllegalStateException("A user already exists with that username");
				}	
			}
			users.add(cred);
		}
		
	}
}


