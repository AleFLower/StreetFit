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
    
    //logica di controllo, perché da FX o da CLI potrei inserire roba che è diversa da casi previsti nello switch del controller applicativo
    //un po forzata come cosa, magari vedere se ci sono alternative piu eleganti
    
    public void handleInput(String ticketType) {
    	if(ticketType.startsWith("Basic") || ticketType.startsWith("basic")) {
    		this.ticketType = "basic";
    	}
    	
    	if(ticketType.startsWith("Special") || ticketType.startsWith("special")) {
    		this.ticketType = "special";
    	}
    	
    	if(ticketType.startsWith("Vip") || ticketType.startsWith("vip")) {
    		this.ticketType = "vip";
    	}
    }
}
