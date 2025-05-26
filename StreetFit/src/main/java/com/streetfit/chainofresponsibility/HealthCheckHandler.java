package main.java.com.streetfit.chainofresponsibility;

import main.java.com.streetfit.model.HealthForm;

public abstract class HealthCheckHandler {

	protected HealthCheckHandler next;
	
	public void setNext(HealthCheckHandler next) {
        this.next = next;
    }
	public abstract boolean handle(HealthForm form);
}
