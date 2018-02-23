package com.solvina.esf.data;


import java.time.LocalDateTime;

/**
 * User: Vlastimil
 * Date: 2/23/18
 * Time: 9:46 AM
 */
public class MessageRequest {

    private LocalDateTime created = LocalDateTime.now();
    private String text;
    private boolean isPing = false;

    public LocalDateTime getCreated() {
        return created;
    }

    public void setCreated(LocalDateTime created) {
        this.created = created;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public boolean isPing() {
        return isPing;
    }

    public void setPing(boolean ping) {
        isPing = ping;
    }

    @Override
    public String toString() {
        return "MessageRequest{" +
                "created=" + created +
                ", text='" + text + '\'' +
                ", isPing=" + isPing +
                '}';
    }
}
