package org.example.promenergosvet.service;

import org.example.promenergosvet.entity.Product;
import org.example.promenergosvet.repo.BasketRepo;
import org.example.promenergosvet.repo.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductService {

    @Autowired
    private final ProductRepo productRepo;


    public ProductService(ProductRepo productRepo) {
        this.productRepo = productRepo;
    }

    public Page<Product> getAllProductByAdditionId(int page, int size, String sort, Long id) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sort));
        return productRepo.findProductByAdditionId(pageable, id);
    }

    public Product getProductById(Long id) {
        return productRepo.findById(id).orElse(null);
    }

}
