package com.streetfit.controllercli;

import com.streetfit.model.Credentials;

public class StartControllerCLI {

	public static void start() {
        LoginControllerCLI controller = new LoginControllerCLI();
		
		controller.start();
		Credentials cred = controller.getCred();  
		HomeGUIControllerCLI.start(cred);
	}
}
