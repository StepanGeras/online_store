package org.example.promenergosvet.repo.basket;

import org.example.promenergosvet.entity.basket.BasketItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BasketItemRepo extends JpaRepository<BasketItem, Long> {

}
