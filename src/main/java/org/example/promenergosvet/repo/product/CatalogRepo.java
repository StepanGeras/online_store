package org.example.promenergosvet.repo.product;

import org.example.promenergosvet.entity.product.Catalog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface CatalogRepo extends JpaRepository<Catalog, Long> {


}
