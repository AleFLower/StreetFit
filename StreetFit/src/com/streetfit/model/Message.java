package com.streetfit.model;

public class Message {

    private String fromUser;  // L'utente che ha inviato il messaggio (participant o trainer)
    private String content;   // Il contenuto del messaggio
    private String reply;     // La risposta del trainer (se presente)
 

    // Costruttore
    public Message(String fromUser, String content) {
        this.fromUser = fromUser;
        this.content = content;
        this.reply = "";  // Risposta vuota di default
    }

 // Costruttore con 'fromUser', 'content' e 'reply'
    public Message(String fromUser, String content, String reply) {
        this.fromUser = fromUser;
        this.content = content;
        this.reply = reply;
    }
    // Getter per 'fromUser'
    public String getFromUser() {
        return fromUser;
    }

    // Setter per 'fromUser'
    public void setFromUser(String fromUser) {
        this.fromUser = fromUser;
    }

    // Getter per 'content'
    public String getContent() {
        return content;
    }

    // Setter per 'content'
    public void setContent(String content) {
        this.content = content;
    }

    // Getter per 'reply'
    public String getReply() {
        return reply;
    }

    // Setter per 'reply'
    public void setReply(String reply) {
        this.reply = reply;
    }

    // Metodo per verificare se il messaggio ha una risposta
    public boolean hasReply() {
        return !this.reply.isEmpty();
    }


}

