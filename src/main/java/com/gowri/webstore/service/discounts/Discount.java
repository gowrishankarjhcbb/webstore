package com.gowri.webstore.service.discounts;

import com.gowri.webstore.domain.Purchase;

public interface Discount {
    void apply(Purchase purchase);
}
