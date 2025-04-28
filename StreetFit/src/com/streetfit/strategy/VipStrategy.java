package com.streetfit.strategy;

import com.streetfit.decorator.Ticket;

public class VipStrategy implements TicketStrategy{
	
    public double applyEvents(Ticket ticket) {
        int quantity = ticket.getQuantity();
        double pricePerTicket = ticket.getPrice();
        
        int halfPriceTickets = quantity / 3;  // ogni 3 biglietti --> 1 al 50%
        int freeTickets = quantity / 10;      // ogni 10 biglietti --> 1 gratis

        // Prezzo totale:
        double total = (quantity * pricePerTicket) 
                       - (halfPriceTickets * pricePerTicket * 0.5)  // sconto 50%
                       - (freeTickets * pricePerTicket);            // biglietti gratis
        
        int effectiveQuantity = quantity - freeTickets; 
        ticket.setQuantity(effectiveQuantity);

        return total;
    }
}
