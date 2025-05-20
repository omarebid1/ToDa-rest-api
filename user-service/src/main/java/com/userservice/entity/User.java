package com.userservice.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.security.core.userdetails.UserDetails;

@Entity
@Table(name = "user")
@Data
public abstract class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;

    private String password;

    private boolean enabled;

}

