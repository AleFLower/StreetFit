package main.java.com.streetfit.strategy;

import main.java.com.streetfit.decorator.Ticket;

public interface TicketStrategy {
   double applyEvents(Ticket ticket);
   
}
