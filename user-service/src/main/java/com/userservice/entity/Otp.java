package com.userservice.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "otp")
@Data
public class Otp {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "otp")
    private String otp;

    @Column(name = "expiration_time")
    private LocalDateTime expirationTime;

    @Column(name = "is_used")
    private boolean used;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

}

