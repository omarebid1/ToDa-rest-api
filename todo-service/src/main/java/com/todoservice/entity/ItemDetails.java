package com.todoservice.entity;

import com.todoservice.enums.Priority;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "item_details")
@Data
@NoArgsConstructor
public class ItemDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String description;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Enumerated(EnumType.STRING)
    @Column(name = "priority")
    private Priority priority;

    @Column(name = "is_completed")
    private Boolean status;

    public ItemDetails(String description, Boolean status, Priority priority, LocalDateTime createdAt) {
        this.description = description;
        this.status = status;
        this.priority = priority;
        this.createdAt = createdAt;
    }
}
