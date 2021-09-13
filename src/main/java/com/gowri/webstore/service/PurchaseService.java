package com.gowri.webstore.service;

import com.gowri.webstore.domain.Product;
import com.gowri.webstore.domain.Purchase;
import com.gowri.webstore.domain.PurchaseRequest;
import com.gowri.webstore.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class PurchaseService {

    private final ProductRepository productRepository;
    private final BillCalculationService billCalculationService;

    public PurchaseService(ProductRepository productRepository, BillCalculationService billCalculationService) {
        this.productRepository = productRepository;
        this.billCalculationService = billCalculationService;
    }

    public Purchase checkBill(PurchaseRequest purchaseRequest) {
        Map<Long, Integer> purchasedProductIdToQuantityMap = purchaseRequest.getPurchaseItems()
                .stream()
                .collect(Collectors.toMap(PurchaseRequest.PurchaseItem::getProductId,
                        PurchaseRequest.PurchaseItem::getQuantity));
        Iterable<Product> purchasedProducts = productRepository.findAllById(purchasedProductIdToQuantityMap.keySet());
        Set<Purchase.Item> purchaseItems = new HashSet<>(purchasedProductIdToQuantityMap.size());
        purchasedProducts.forEach(product -> {
            Purchase.Item item = new Purchase.Item();
            item.setProduct(product);
            item.setTotalNoOfSingleUnits(purchasedProductIdToQuantityMap.get(product.getId()));
            purchaseItems.add(item);
        });
        Purchase purchase = new Purchase();
        purchase.setItems(purchaseItems);
        billCalculationService.calculateBill(purchase);
        return purchase;

    }
}
