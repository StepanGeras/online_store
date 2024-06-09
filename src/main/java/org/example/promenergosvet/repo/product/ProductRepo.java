package org.example.promenergosvet.repo.product;

import org.example.promenergosvet.entity.product.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepo extends JpaRepository<Product, Long> {

    Page<Product> findProductByAdditionId(Pageable pageable, Long id);
}
