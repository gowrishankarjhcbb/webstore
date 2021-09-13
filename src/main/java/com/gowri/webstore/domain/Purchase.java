package com.gowri.webstore.domain;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Set;

@Data
public class Purchase {
    private Set<Item> items;
    private BigDecimal subTotal;
    private BigDecimal total;

    @Getter @Setter
    public static class Item {
        private Product product;
        private int noOfCartons;
        private int noOfSingleUnits;
        private int totalNoOfSingleUnits;
        private BigDecimal cartonPrice;
        private BigDecimal singleUnitsPrice;
    }



}
