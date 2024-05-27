package org.example.promenergosvet.entity;

import lombok.Data;

import jakarta.persistence.*;

@Data
@Entity
@Table(name = "home_page")
public class Home {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String title;
    private String image;

}
