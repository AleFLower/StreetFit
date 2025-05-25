package com.streetfit.main;

import com.streetfit.controllercli.StartControllerCLI;

public class MainCLI {
	private MainCLI() {
        throw new UnsupportedOperationException("Cannot instantiate MainCLI");
    }

	public static void run() {
		
		StartControllerCLI.start();
		
	}

}
