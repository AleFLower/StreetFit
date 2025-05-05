package com.streetfit.model;

public class Participation {
    private String username;   // chi si è iscritto
    private String stage;      // a quale stage
    private int ticket;        // tipo di ticket acquistato

    public Participation(String username, String stage, int ticket) {
        this.username = username;
        this.stage = stage;
        this.ticket = ticket;
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

    @Override
    public String toString() {
        return "Participation{" +
                "username='" + username + '\'' +
                ", stage='" + stage + '\'' +
                ", ticket=" + ticket +
                '}';
    }
}

