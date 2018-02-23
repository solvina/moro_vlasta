package com.solvina.esf.data;


import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

/**
 * User: Vlastimil
 * Date: 2/23/18
 * Time: 10:19 AM
 */
@Entity
@Table(name = "message")
public class Message {
    @Id
   	@Column(name="id")
   	@GeneratedValue(strategy= GenerationType.IDENTITY)
   	private Long id;

    @NotEmpty
    @Size(min=2, max=3000)
    private String message;

    private LocalDateTime created = LocalDateTime.now();

    private LocalDateTime received;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public void setCreated(LocalDateTime created) {
        this.created = created;
    }

    public LocalDateTime getReceived() {
        return received;
    }

    public void setReceived(LocalDateTime received) {
        this.received = received;
    }

    @Override
    public String toString() {
        return "Message{" +
                "id=" + id +
                ", message='" + message + '\'' +
                ", created=" + created +
                ", received=" + received +
                '}';
    }
}
