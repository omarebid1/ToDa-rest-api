package com.todoservice.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "items")
@Data
@NoArgsConstructor
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

    public Item(String title, Long userId, ItemDetails itemDetails) {
        this.title = title;
        this.userId = userId;
        this.itemDetails = itemDetails;
    }
}
