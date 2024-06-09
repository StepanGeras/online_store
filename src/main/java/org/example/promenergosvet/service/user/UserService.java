package org.example.promenergosvet.service.user;

import org.example.promenergosvet.entity.user.User;
import org.example.promenergosvet.repo.user.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class UserService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User findByUsername(String username) {
        return userRepo.findByUsername(username);
    }

//    public void save(User user) {
//        userRepo.save(user);
//    }

    public List<User> findAll() {
        return userRepo.findAll();
    }

    public void save(User user) {
        user.setRoles(Set.of(User.Role.ROLE_USER));
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepo.save(user);
    }
}
