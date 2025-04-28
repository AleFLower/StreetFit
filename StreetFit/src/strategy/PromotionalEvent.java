package strategy;

import com.streetfit.decorator.Ticket;

public class PromotionalEvent implements TicketStrategy {

	@Override
	public double applyEvents(Ticket ticket) {
	
		
		int quantity = ticket.getQuantity();
		int k = 0;
		
		for(int i = 0; i < quantity;i++) {
			if(i % 5 == 0) k++;  //ogni 5 iscritti, un biglietto gratis
		}
		ticket.setQuantity(quantity-k);
		return ticket.getPrice() * (quantity-k);
	}
	
}
