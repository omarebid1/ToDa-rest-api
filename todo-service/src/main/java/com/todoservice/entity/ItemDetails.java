package com.todoservice.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "item_details")
@Data
public class ItemDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String description;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    private String priority;

    private String status;

    // Getters and Setters
    // ...
}
