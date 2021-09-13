package com.gowri.webstore.domain;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Data
public class PurchaseRequest {
    List<PurchaseItem> purchaseItems;

    @Data
    public static class PurchaseItem {
        private Long productId;
        private int quantity;
    }
}
