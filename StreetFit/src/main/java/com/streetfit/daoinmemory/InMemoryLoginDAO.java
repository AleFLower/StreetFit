package main.java.com.streetfit.daoinmemory;


import java.util.ArrayList;
import java.util.List;

import main.java.com.streetfit.dao.LoginDao;
import main.java.com.streetfit.exception.DAOException;
import main.java.com.streetfit.model.Credentials;
import main.java.com.streetfit.model.Role;

public class InMemoryLoginDAO implements LoginDao {

	// Lista condivisa tra tutte le istanze della classe
    private static List<Credentials> users = new ArrayList<>();

    // Costruttore: inizializza gli utenti predefiniti solo una volta
    public InMemoryLoginDAO() {
    
        if (users.isEmpty()) {
            users.add(new Credentials("trainer", "trainer", Role.TRAINER));
            users.add(new Credentials("participant", "participant", Role.PARTICIPANT));
            users.add(new Credentials("participant2", "participant2", Role.PARTICIPANT));
            users.add(new Credentials("participant3", "participant3", Role.PARTICIPANT));
        }
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




