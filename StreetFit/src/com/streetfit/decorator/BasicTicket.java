package com.streetfit.decorator;

public class BasicTicket implements Ticket {
	
	private int quantity;
	
	public BasicTicket(int quantity) {
		this.quantity = quantity;
	}
    @Override
    public String getDescription() {
        return "Basic Ticket (only entry)";
    }

    @Override
    public double getPrice() {
        return 10.0; // Prezzo base
    }
    public int getQuantity() {
    	return this.quantity;
    }
    public void setQuantity(int quantity) {
    	this.quantity = quantity;
    }
}