package main.java.com.streetfit.strategy;

import main.java.com.streetfit.decorator.Ticket;

public class StandardTicket implements TicketStrategy{

	@Override
	public double applyEvents(Ticket ticket) {
		
		return ticket.getQuantity() * ticket.getPrice();
	}
	
}
