package main.java.com.streetfit.model;

public class Participation {
    private String username;   // chi si è iscritto
    private String stage;      // a quale stage
    private int ticket;        // tipo di ticket acquistato
    private double total;
    

    public Participation(String username, String stage, int ticket, double total) {
        this.username = username;
        this.stage = stage;
        this.ticket = ticket;
        this.total = total;
    }

    public String getUsername() {
        return username;
    }

    public String getStage() {
        return stage;
    }

    public int getTicket() {
        return ticket;
    }

    public double getTotal() {
    	return total;
    }
    @Override
    public String toString() {
        return "Participation{" +
                "username='" + username + '\'' +
                ", stage='" + stage + '\'' +
                ", ticket=" + ticket +
                '}';
    }
}

