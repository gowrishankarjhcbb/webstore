package com.gowri.webstore.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.math.BigDecimal;

@Data
@Entity
@NoArgsConstructor
public class Product {
    private @Id @GeneratedValue Long id;
    private String name;
    private BigDecimal pricePerCarton;
    private int noOfUnitsInCarton;

    public Product(String name, BigDecimal pricePerCarton, int noOfUnitsInCarton) {
        this.name = name;
        this.pricePerCarton = pricePerCarton;
        this.noOfUnitsInCarton = noOfUnitsInCarton;
    }
}
