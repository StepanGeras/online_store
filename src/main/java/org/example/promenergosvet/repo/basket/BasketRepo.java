package org.example.promenergosvet.repo.basket;


import jakarta.transaction.Transactional;
import org.example.promenergosvet.entity.basket.Basket;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Set;

public interface BasketRepo extends JpaRepository<Basket, Long> {

    Basket findByUserId(Long id);
    @Transactional
    void deleteByUserId(Long id);

    List<Basket> findAllByUserId(Long userId);

    Basket findByUserIdAndRoles(Long user_id, Set<Basket.Role> roles);
}
