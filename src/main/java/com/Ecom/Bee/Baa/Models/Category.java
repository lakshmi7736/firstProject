package com.Ecom.Bee.Baa.Models;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Category")
@Getter@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int categoryId;

    private String categoryName;

    private String categoryDescription;

    @OneToMany(mappedBy = "category")
    private List<Product> products= new ArrayList<>();
}
