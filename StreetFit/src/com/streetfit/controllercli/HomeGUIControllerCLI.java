package com.streetfit.controllercli;

import com.streetfit.model.Credentials;

public class HomeGUIControllerCLI {

	
	public static void start(Credentials cred) {
		
		if(cred.getRole() == null) {
			throw new IllegalStateException("Invalid credentials: unknown role " + cred.getRole());

        }
		switch(cred.getRole()) { 
		case TRAINER -> new TrainerControllerCLI().start();
		case PARTICIPANT -> new ParticipantControllerCLI(cred).start();
		default -> throw new IllegalArgumentException("Error during authentication: unknown role " + cred.getRole());
		}
}
}