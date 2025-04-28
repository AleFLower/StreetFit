package com.streetfit.decorator;


public class SpecialTicket extends TicketDecorator {
    public SpecialTicket(Ticket ticket) {
        super(ticket);
    }

    @Override
    public String getDescription() {
        return super.getDescription() + ", T-shirt and Gym Bag";
    }

    @Override
    public double getPrice() {
        return super.getPrice() + 10.0;  // aggiunta al prezzo
    }
    public int getQuantity() {
    	return super.getQuantity();
    }
    
    public void setQuantity(int quantity) {
    	super.setQuantity(quantity);
    }
}
