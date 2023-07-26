package com.Ecom.Bee.Baa.Models;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "Products")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int productId;

    private String pName;

    @Column(length = 3000)
    private String pDescription;

    private String pPhoto;

    private double pPrice;

    private double pDiscount;

    private int pQuantity;
    @ManyToOne
    private Category category;


}
