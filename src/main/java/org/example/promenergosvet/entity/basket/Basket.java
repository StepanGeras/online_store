package org.example.promenergosvet.entity.basket;

import jakarta.persistence.*;
import lombok.Data;
import org.example.promenergosvet.converter.BasketRoleSetConverter;
import org.example.promenergosvet.entity.product.Product;
import org.example.promenergosvet.entity.user.User;
import org.springframework.security.core.GrantedAuthority;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Data
@Entity
public class Basket implements Serializable {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "basket")
    private List<BasketItem> items = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Convert(converter = BasketRoleSetConverter.class)
    private Set<Basket.Role> roles;

    public enum Role implements GrantedAuthority {
        ROLE_DESIGN, ROLE_ISSUED;

        @Override
        public String getAuthority() {
            return this.name();
        }
    }

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
