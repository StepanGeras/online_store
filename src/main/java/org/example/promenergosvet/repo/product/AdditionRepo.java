package org.example.promenergosvet.repo.product;

import org.example.promenergosvet.entity.product.Addition;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AdditionRepo extends JpaRepository<Addition, Long> {

    List<Addition> findAllByCatalogId(Long id, Sort sort);

    @Query ("select a.id from Addition a where a.ancillary = :ancillary")
    Long findIdByAncillary(@Param("ancillary") String ancillary);

    Addition findAdditionByCatalogId(Long id);

    Addition findAdditionByAncillary(String ancillary);
}
