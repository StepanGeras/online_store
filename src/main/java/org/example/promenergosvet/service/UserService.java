package org.example.promenergosvet.service;

import lombok.AllArgsConstructor;
import org.example.promenergosvet.entity.User;
import org.example.promenergosvet.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepo userRepo;

    public User findBySurname(String surname) {
        return userRepo.findBySurname(surname);
    }

    public void save(User user) {
        userRepo.save(user);
    }

    public List<User> findAll() {
        return userRepo.findAll();
    }
}
