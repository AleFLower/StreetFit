package main.java.com.streetfit.beans;

public class TicketBean {
    private String ticketType;
    private int quantity;

    public TicketBean(String ticketType, int quantity) {
        this.ticketType = ticketType.toLowerCase();
        this.quantity = quantity;
    }

    public String getTicketType() {
        return ticketType;
    }

    public int getQuantity() {
        return quantity;
    }
}
