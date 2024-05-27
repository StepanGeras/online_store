package org.example.promenergosvet.service;

import org.example.promenergosvet.entity.Addition;
import org.example.promenergosvet.repo.AdditionRepo;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class AdditionService  {

    private final AdditionRepo additionRepo;

    public AdditionService(AdditionRepo additionRepo) {
        this.additionRepo = additionRepo;
    }

    public List<Addition> findAdditionByCatalogId(Long catalogId) {
        Sort sort = Sort.by(Sort.Direction.ASC, "id");
        return additionRepo.findAllByCatalogId(catalogId, sort);

    }


    public Long findIdByAncillary(String addition) {

        return additionRepo.findIdByAncillary(addition);

    }
}
