package org.example.promenergosvet.service;

import org.example.promenergosvet.entity.Basket;
import org.example.promenergosvet.entity.Product;
import org.example.promenergosvet.entity.User;
import org.example.promenergosvet.repo.BasketRepo;
import org.example.promenergosvet.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BasketService {

    @Autowired
    private BasketRepo basketRepo;

    @Autowired
    private ProductService productService;

    @Autowired
    private UserService userService;

    public Basket getBasket(Long cartId) {
        return basketRepo.findById(cartId).orElse(new Basket());
    }

    public Basket addToCart(Long productId, Basket basket) {
        Product product = productService.getProductById(productId);
        basket.addItem(product);
        return basketRepo.save(basket);
    }

    public void saveBasketId(User user) {

        userService.save(user);

    }

    public Basket removeFromCart(Long basketId, Long productId) {
        Basket basket = getBasket(basketId);
        basket.removeItem(productId);
        return basketRepo.save(basket);
    }

}
