package com.gowri.webstore.service.discounts.impl;

import com.gowri.webstore.domain.Purchase;
import com.gowri.webstore.service.discounts.Discount;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class FixDiscount implements Discount {

    private final double discountPercentage = 0.10;

    @Override
    public void apply(Purchase purchase) {
        purchase.getItems().stream()
                .filter(purchaseItem -> purchaseItem.getNoOfCartons() >= 3)
                .findAny()
                .ifPresent(item -> applyDiscountForItem(purchase));
    }

    private void applyDiscountForItem(Purchase purchase) {
        BigDecimal discount = purchase.getTotal()
                .multiply(BigDecimal.valueOf(discountPercentage));
        purchase.setTotal(purchase.getTotal()
                .subtract(discount));
    }
}
