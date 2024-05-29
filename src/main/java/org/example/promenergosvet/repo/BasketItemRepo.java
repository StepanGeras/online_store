package org.example.promenergosvet.repo;

import org.example.promenergosvet.entity.BasketItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BasketItemRepo extends JpaRepository<BasketItem, Long> {
}
