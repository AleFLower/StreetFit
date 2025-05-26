package main.java.com.streetfit.controllercli;

import main.java.com.streetfit.model.Credentials;

public class HomeGUIControllerCLI {
	
	private HomeGUIControllerCLI() {}

	
	  public static void start() {
	        while (true) {
	            Credentials cred = loginAndGetCredentials();
	            
	            if (cred.getRole() == null) {
	                continue; // Restart login
	            }

	            switch (cred.getRole()) {
	                case TRAINER -> new TrainerControllerCLI().start();
	                case PARTICIPANT -> new ParticipantControllerCLI(cred).start();
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