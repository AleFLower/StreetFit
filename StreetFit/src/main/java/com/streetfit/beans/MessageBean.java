package main.java.com.streetfit.beans;

public class MessageBean {

    private String fromUser;
    private String content;
    private String reply;

    // Costruttore
    public MessageBean(String fromUser, String content) {
        this.fromUser = fromUser;
        this.content = content;
        this.reply = "";  // Risposta vuota di default
    }

    // Costruttore con tutti i campi
    public MessageBean(String fromUser, String content, String reply) {
        this.fromUser = fromUser;
        this.content = content;
        this.reply = reply;
    }

    // Getter e Setter
    public String getFromUser() {
        return fromUser;
    }

    public void setFromUser(String fromUser) {
        this.fromUser = fromUser;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getReply() {
        return reply;
    }

    public void setReply(String reply) {
        this.reply = reply;
    }

    // Metodo per verificare se il messaggio ha una risposta
    public boolean hasReply() {
        return !this.reply.isEmpty();
    }

    @Override
    public String toString() {
        return "MessageBean{" +
                "fromUser='" + fromUser + '\'' +
                ", content='" + content + '\'' +
                ", reply='" + reply + '\'' +
                '}';
    }
}
