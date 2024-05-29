package org.example.promenergosvet.entity;


import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "basket_item")
public class BasketItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

}
