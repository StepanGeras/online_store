package org.example.promenergosvet.entity;

import lombok.Data;
import jakarta.persistence.*;


@Data
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String surname;
    private String telephone;

}
