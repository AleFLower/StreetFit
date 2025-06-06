package main.java.com.streetfit.chainofresponsibility;

import main.java.com.streetfit.model.HealthForm;

public class FitnessCheckHandler extends HealthCheckHandler {
	public boolean handle(HealthForm form) {
		if(form.hasHeartIssues()) {
			return false;
		}
		
		if(next != null) {
			return next.handle(form);
		}
		return true; 
	}
}
