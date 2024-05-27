package org.example.promenergosvet.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "addition")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Addition {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn (name = "id_catalog")
    private Catalog catalog;

    private String ancillary;

    @ToString.Exclude
    @OneToMany (mappedBy = "addition")
    private List<Product> productList;



}
