package org.example.promenergosvet.service.basket;

import jakarta.transaction.Transactional;
import org.example.promenergosvet.entity.basket.Basket;
import org.example.promenergosvet.entity.basket.BasketItem;
import org.example.promenergosvet.repo.basket.BasketItemRepo;
import org.example.promenergosvet.repo.basket.BasketRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class BasketService {

    @Autowired
    private BasketRepo basketRepo;

    @Autowired
    private BasketItemRepo basketItemRepo;

    public Basket getBasketByUserId(Long userId) {
        return basketRepo.findByUserId(userId);
    }

    public Basket getBasketByUserIdBasketRole (Long userId, Set<Basket.Role> roles) {
        return basketRepo.findByUserIdAndRoles(userId, roles);
    }

    public List<Basket> getAllBaskets() {
        return basketRepo.findAll();
    }

    public List<Basket> getAllBasketByUserId(Long userId) {
        return basketRepo.findAllByUserId(userId);
    }

    public List<BasketItem> getBasketItemByBasketId(Long basketId) {
        return basketItemRepo.findByBasketId(basketId);
    }

    @Transactional
    public void deleteBasketByUserId(Long userId) {
        basketRepo.deleteByUserId(userId);
    }

    public Basket save(Basket basket) {
        return basketRepo.save(basket);
    }

    public void saveBasketItem(BasketItem basketItem) {
        basketItemRepo.save(basketItem);
    }

}
