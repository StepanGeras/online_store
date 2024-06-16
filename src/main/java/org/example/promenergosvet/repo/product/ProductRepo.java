package org.example.promenergosvet.repo.product;

import org.example.promenergosvet.entity.product.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepo extends JpaRepository<Product, Long> {

    Page<Product> findProductByAdditionId(Pageable pageable, Long id);
    List<Product> findByNameContainingIgnoreCaseOrTextContainingIgnoreCase(String nameKeyword, String textKeyword);
}
