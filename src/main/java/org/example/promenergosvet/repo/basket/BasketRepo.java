package org.example.promenergosvet.repo.basket;


import org.example.promenergosvet.entity.basket.Basket;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BasketRepo extends JpaRepository<Basket, Long> {

    Basket findByUserId(Long id);

}
