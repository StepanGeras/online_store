package org.example.promenergosvet.repo.user;

import org.example.promenergosvet.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepo extends JpaRepository<User, Long> {

    User findByUsername(String username);
    User findByTelephone(String telephone);
    List<User> findAllByUsername(String username);
    User findByEmail(String email);
}
