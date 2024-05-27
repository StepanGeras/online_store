package org.example.promenergosvet.repo;

import org.example.promenergosvet.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepo extends JpaRepository<User, Long> {

    User findBySurname(String surname);

}
