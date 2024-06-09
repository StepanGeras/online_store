package org.example.promenergosvet.service.basket;

import org.example.promenergosvet.entity.basket.Basket;
import org.example.promenergosvet.repo.basket.BasketRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BasketService {

    @Autowired
    private BasketRepo basketRepo;

    public Basket getBasketByUserId(Long userId) {
        return basketRepo.findByUserId(userId);
    }

    public void deleteBasketByUserId(Long userId) {
        basketRepo.deleteById(userId);
    }

    public void save(Basket basket) {
        basketRepo.save(basket);
    }

}
