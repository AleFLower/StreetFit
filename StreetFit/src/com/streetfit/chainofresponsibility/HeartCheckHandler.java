package com.streetfit.chainofresponsibility;

import com.streetfit.model.HealthForm;

public class HeartCheckHandler extends HealthCheckHandler{

	public boolean handle(HealthForm form) {
		if(form.hasHeartIssues()) {
			return false;
		}
		
		if(next != null) {
			return next.handle(form);  //se ho un successore nella catena, delego a lui il compito
		}
		return true; //altrimenti tutti i controlli sono ok e ritorno true
	}
}
