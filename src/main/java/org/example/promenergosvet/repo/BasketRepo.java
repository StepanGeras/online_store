package org.example.promenergosvet.repo;

import org.example.promenergosvet.entity.Basket;
import org.example.promenergosvet.entity.Product;
import org.example.promenergosvet.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface BasketRepo extends JpaRepository<Basket, Long> {

    @Query("SELECT b.product.id FROM Basket b WHERE b.user.id = :userId")
    List<Long> findAllProductIdByUserId(@Param("userId") Long userId);


}
