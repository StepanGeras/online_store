package org.example.promenergosvet.repo;

import org.example.promenergosvet.entity.Home;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface HomeRepo extends JpaRepository<Home, Long> {

}
