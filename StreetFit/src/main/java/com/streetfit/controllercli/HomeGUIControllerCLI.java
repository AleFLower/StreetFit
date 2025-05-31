package main.java.com.streetfit.controllercli;

import main.java.com.streetfit.model.Credentials;
import main.java.com.streetfit.utils.NotificationQueue;

public class HomeGUIControllerCLI {
	
	private final static NotificationQueue queue = new NotificationQueue();
	 
	  public static void start() {
		  
	        while (true) {
	            Credentials cred = loginAndGetCredentials();
	            
	            if (cred.getRole() == null) {
	                continue; // Restart login
	            }

	            switch (cred.getRole()) {
	                case TRAINER -> new TrainerControllerCLI(queue).start();
	                case PARTICIPANT -> new ParticipantControllerCLI(cred,queue).start();
	                default -> throw new IllegalArgumentException("Unknown role: " + cred.getRole());
	            }
	        }
	    }

	    private static Credentials loginAndGetCredentials() {
	        LoginControllerCLI controller = new LoginControllerCLI();
	        controller.start();
	        return controller.getCred();
	    }
}