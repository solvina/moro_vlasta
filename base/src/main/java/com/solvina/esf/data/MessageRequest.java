package com.solvina.esf.data;


import java.time.LocalDateTime;

/**
 * User: Vlastimil
 * Date: 2/23/18
 * Time: 9:46 AM
 */
public class MessageRequest {

    private LocalDateTime created;
    private String text;

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
}
