package org.example.promenergosvet.service.product;

import org.example.promenergosvet.entity.product.Catalog;
import org.example.promenergosvet.repo.product.CatalogRepo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class CatalogService {

    private final CatalogRepo catalogRepo;

    public CatalogService(CatalogRepo catalogRepo) {
        this.catalogRepo = catalogRepo;
    }

    public Page<Catalog> getAllProduct(int page, int size, String sort) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sort));
        return catalogRepo.findAll(pageable);
    }

}

