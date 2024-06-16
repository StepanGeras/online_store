package org.example.promenergosvet.entity.basket;


import jakarta.persistence.*;
import lombok.Data;
import org.example.promenergosvet.entity.product.Product;

import java.util.Date;

@Data
@Entity
@Table(name = "basket_item")
public class BasketItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "basket_id")
    private Basket basket;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @Temporal(TemporalType.TIMESTAMP)
    private Date date;

}
