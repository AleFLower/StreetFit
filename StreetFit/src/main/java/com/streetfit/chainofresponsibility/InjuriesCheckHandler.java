package main.java.com.streetfit.chainofresponsibility;

import main.java.com.streetfit.model.HealthForm;

public class InjuriesCheckHandler extends HealthCheckHandler{

	public boolean handle(HealthForm form) {
		if(form.hasInjuries()) {
			return false;
		}
		
		if(next != null) {
			return next.handle(form);
		}
		return true;
	}
}
