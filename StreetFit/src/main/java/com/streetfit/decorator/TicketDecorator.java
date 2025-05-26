package main.java.com.streetfit.decorator;

public abstract class TicketDecorator implements Ticket {
    protected Ticket decoratedTicket;

    protected TicketDecorator(Ticket decoratedTicket) {
        this.decoratedTicket = decoratedTicket;
    }

    @Override
    public String getDescription() {
        return decoratedTicket.getDescription();
    }

    @Override
    public double getPrice() {
        return decoratedTicket.getPrice();
    }
    public int getQuantity() {
    	return decoratedTicket.getQuantity();
    }
    public void setQuantity(int quantity) {
    	decoratedTicket.setQuantity(quantity);
    }
}
