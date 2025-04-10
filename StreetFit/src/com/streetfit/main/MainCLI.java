package com.streetfit.main;

import com.streetfit.controllercli.HomeGUIControllerCLI;

public class MainCLI {
	private MainCLI() {
        throw new UnsupportedOperationException("Cannot instantiate MainCLI");
    }

	public static void run() {
		
		HomeGUIControllerCLI controller = new HomeGUIControllerCLI();
		controller.start();
		
	}

}
