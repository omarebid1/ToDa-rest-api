package com.todoservice.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "items_table")
@Data
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @Column(name = "user_id")
    private Long userId;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "item_details_id")
    private ItemDetails itemDetails;

    @Column(name = "is_completed")
    private Boolean isCompleted;

}
