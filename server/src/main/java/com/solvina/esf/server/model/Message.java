package com.solvina.esf.server.model;

import javax.persistence.*;


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

    private String message;

//    private LocalDateTime created;
//
//    private LocalDateTime received = LocalDateTime.now();


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

//    public LocalDateTime getCreated() {
//        return created;
//    }
//
//    public void setCreated(LocalDateTime created) {
//        this.created = created;
//    }
//
//    public LocalDateTime getReceived() {
//        return received;
//    }
//
//    public void setReceived(LocalDateTime received) {
//        this.received = received;
//    }
}
