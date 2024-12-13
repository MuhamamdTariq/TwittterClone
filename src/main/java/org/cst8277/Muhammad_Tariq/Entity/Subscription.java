package org.cst8277.Muhammad_Tariq.Entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

import org.hibernate.annotations.UuidGenerator;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
public class Subscription {

    @Id
    @UuidGenerator
    private String id;

    @ManyToOne
    @JoinColumn(name = "subscriber_id", nullable = false)
    private Users subscriber;

    @ManyToOne
    @JoinColumn(name = "producer_id", nullable = false)
    @JsonIgnoreProperties({"producer", "id", "password"})
    private Users producer;

    @Column(nullable = false, updatable = false)
    private LocalDateTime subscribedAt = LocalDateTime.now();

 // Getters and Setters
    public String getId() {
        return id;
    }


    public Users getSubscriber() {
        return subscriber;
    }

    public void setSubscriber(Users subscriber) {
        this.subscriber = subscriber;
    }

    public Users getProducer() {
        return producer;
    }

    public void setProducer(Users producer) {
        this.producer = producer;
    }
}