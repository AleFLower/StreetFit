package com.streetfit.decorator;


public class VipTicket extends TicketDecorator {
    public VipTicket(Ticket ticket) {
        super(ticket);
    }

    @Override
    public String getDescription() {
        return super.getDescription() + ", Headphones and Shoes";
    }

    @Override
    public double getPrice() {
        return super.getPrice() + 25.0;  // aggiunta al prezzo
    }
   
}
