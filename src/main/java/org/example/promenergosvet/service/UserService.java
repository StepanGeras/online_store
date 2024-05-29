package org.example.promenergosvet.service;

import org.example.promenergosvet.entity.Basket;
import org.example.promenergosvet.entity.User;
import org.example.promenergosvet.repo.BasketRepo;
import org.example.promenergosvet.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private BasketRepo basketRepo;

    public User findBySurname(String surname) {
        return userRepo.findBySurname(surname);
    }

//    public void save(User user) {
//        userRepo.save(user);
//    }

    public List<User> findAll() {
        return userRepo.findAll();
    }

    public void save(User user) {
        userRepo.save(user);
    }
}
