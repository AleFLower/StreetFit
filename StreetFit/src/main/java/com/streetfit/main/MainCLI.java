package main.java.com.streetfit.main;

import main.java.com.streetfit.controllercli.HomeGUIControllerCLI;


public class MainCLI {
	private MainCLI() {
        throw new UnsupportedOperationException("Cannot instantiate MainCLI");
    }

	public static void run() {
		
	HomeGUIControllerCLI.start();
		
	}

}
