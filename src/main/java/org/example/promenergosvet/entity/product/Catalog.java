package org.example.promenergosvet.entity.product;

import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

import java.util.List;

@Data
@Entity
@Table (name = "catalog")
public class Catalog {

    @Id
    @GeneratedValue (strategy =  GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String image;

    @ToString.Exclude
    @OneToMany (mappedBy = "catalog")
    private List<Addition> additions;

}
