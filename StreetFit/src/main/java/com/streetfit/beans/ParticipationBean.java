package main.java.com.streetfit.beans;

import main.java.com.streetfit.model.TrainingStage;

public class ParticipationBean {
    private String username;
    private TrainingStage stage;
    private int ticket;
    private double total;

    // Costruttore con parametri
    public ParticipationBean(String username, TrainingStage stage, int ticket, double total) {
        this.username = username;
        this.stage = stage;
        this.ticket = ticket;
        this.total = total;
    }

    // Getter e Setter
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public TrainingStage getStage() {
        return stage;
    }

    public void setStage(TrainingStage stage) {
        this.stage = stage;
    }

    public int getTicket() {
        return ticket;
    }

    public void setTicket(int ticket) {
        this.ticket = ticket;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    // Override toString (opzionale ma utile per debug/logging)
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
