package com.nazarii.yukhnovskyi.ecommerceProject.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Product {

    @Id
    private int id;
    private String name;
    private String dsc;
    private String brand;
    private BigDecimal price;
    private String category;
    @Column(name = "releasedate")
    private Date releaseDate;
    private int stock;


}
