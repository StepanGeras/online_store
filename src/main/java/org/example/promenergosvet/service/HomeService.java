package org.example.promenergosvet.service;

import org.example.promenergosvet.repo.HomeRepo;
import org.example.promenergosvet.entity.Home;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class HomeService {

    private final HomeRepo homeRepo;

    public HomeService(HomeRepo homeRepo) {
        this.homeRepo = homeRepo;
    }

    public List<Home> getAllHome() {
        Sort sort = Sort.by(Sort.Direction.ASC, "id");
        return homeRepo.findAll(sort);
    }
}
