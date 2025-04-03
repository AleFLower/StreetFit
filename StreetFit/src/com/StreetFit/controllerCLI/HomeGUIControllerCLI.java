package com.streetfit.controllerCLI;

import com.streetfit.model.Credentials;
import com.streetfit.controller.Controller;

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
