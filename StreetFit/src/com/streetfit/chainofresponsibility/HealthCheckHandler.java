package com.streetfit.chainofresponsibility;

import com.streetfit.model.HealthForm;

public abstract class HealthCheckHandler {

	protected HealthCheckHandler next;
	
	public void setNext(HealthCheckHandler next) {
        this.next = next;
    }
	public abstract boolean handle(HealthForm form);
}
