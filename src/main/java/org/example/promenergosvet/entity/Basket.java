package org.example.promenergosvet.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
public class Basket {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(cascade = CascadeType.ALL)
    private List<BasketItem> items = new ArrayList<>();

    public void addItem(Product product) {
        for (BasketItem item : items) {
            if (item.getProduct().getId().equals(product.getId())) {
                return;
            }
        }
        BasketItem newItem = new BasketItem();
        newItem.setProduct(product);
        items.add(newItem);
    }

    public void removeItem(Long productId) {
        items.removeIf(item -> item.getProduct().getId().equals(productId));
    }

}
