package com.StreetFit.beans;

import com.StreetFit.model.Role;

public class CredentialsBean {
	    private final String username;
	    private final String password;
	    private final Role role;

	    public CredentialsBean(String username, String password, Role role) {
	    	
	    	if (!isValidUsername(username)) {
	            throw new IllegalArgumentException("Wrong username. It must contain only numbers and letters");
	        }
	        if (!isValidPassword(password)) {
	            throw new IllegalArgumentException("Wrong password");
	        }
	        
	        this.username = username;
	        this.password = password;
	        this.role = role;
	    }

	    public String getUsername() {
	        return username;
	    }

	    public String getPassword() {
	        return password;
	    }

	    public Role getRole() {
	        return role;
	    }

	    private boolean isValidUsername(String username) {
	    	return username != null;
	    }
	    
	    private boolean isValidPassword(String password) {
	        return password != null;
	    }
}
