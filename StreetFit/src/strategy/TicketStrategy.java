package strategy;

import com.streetfit.decorator.Ticket;

public interface TicketStrategy {
   double applyEvents(Ticket ticket);
   
}
