package com.StreetFit.controllerCLI;

import com.StreetFit.controller.Controller;
import com.StreetFit.model.Credentials;

public class HomeGUIControllerCLI implements Controller{

	private Credentials cred;
	
	public void start() {
		//i have to retrieve login information from loginController		
		LoginControllerCLI controller = new LoginControllerCLI();
		controller.start();
		cred = controller.getCred();   
		
		
		if(cred.getRole() == null) {
            throw new RuntimeException("Invalid credentials");
        }
		
		switch(cred.getRole()) { 
		case TRAINER -> new TrainerControllerCLI().start();
		case PARTICIPANT -> new ParticipantControllerCLI().start();
		default -> throw new RuntimeException("Invalid credentials");
			
		}
}
}
