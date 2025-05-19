package com.streetfit.controllercli;

import com.streetfit.model.Credentials;

public class HomeGUIControllerCLI {

	
	public void start() {
		//i have to retrieve login information from loginController		
		LoginControllerCLI controller = new LoginControllerCLI();
		
		controller.start();
		Credentials cred = controller.getCred();   
		
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
