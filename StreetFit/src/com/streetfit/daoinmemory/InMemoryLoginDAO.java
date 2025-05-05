package com.streetfit.daoinmemory;


import java.util.ArrayList;
import java.util.List;

import com.streetfit.dao.LoginDAO;
import com.streetfit.exception.DAOException;
import com.streetfit.model.Credentials;
import com.streetfit.model.Role;

public class InMemoryLoginDAO implements LoginDAO {

    private final List<Credentials> users;

    public InMemoryLoginDAO() {
        users = new ArrayList<>();
        // Creiamo utenti predefiniti
        users.add(new Credentials("trainer", "trainer", Role.TRAINER));
        users.add(new Credentials("participant", "participant", Role.PARTICIPANT));
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
}


