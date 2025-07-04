package main.java.com.streetfit.model;

public class Participation {
    private String username;
    private TrainingStage stage;  //aggregazione
    private int ticket;
    private double total;

    public Participation(String username, TrainingStage stage, int ticket, double total) {
        this.username = username;
        this.stage = stage;
        this.ticket = ticket;
        this.total = total;
    }

    public String getUsername() {
        return username;
    }

    public TrainingStage getStage() {
        return stage;
    }

    public int getTicket() {
        return ticket;
    }

    public double getTotal() {
        return total;
    }

    public void setTicket(int ticket) {
        this.ticket = ticket;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public void setStage(TrainingStage stage) {
        this.stage = stage;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String toString() {
        return "Participation{" +
                "username='" + username + '\'' +
                ", stage='" + stage + '\'' +
                ", ticket=" + ticket +
                ", total=" + total +
                '}';
    }
}

