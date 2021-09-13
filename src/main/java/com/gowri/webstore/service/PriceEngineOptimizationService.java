package com.gowri.webstore.service;

import com.gowri.webstore.domain.Product;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

@Service
public class PriceEngineOptimizationService {

    public Pair<Integer, Integer> getOptimizedNoOfCartonsAndSingleUnits(Pair<Product, Integer> productToQuantity) {
        Product product = productToQuantity.getFirst();
        int quantity = productToQuantity.getSecond();
        int nofPurchasableCartons = calculateNofPurchasableCartons(product.getNoOfUnitsInCarton(), quantity);
        int nofSingleUnitsToBePurchased = calculateNofSingleUnitsToBePurchased(product.getNoOfUnitsInCarton(), quantity);
        return Pair.of(nofPurchasableCartons, nofSingleUnitsToBePurchased);
    }

    public int calculateNofPurchasableCartons(int noOfUnitsInACarton, int noOfUnitsPurchased) {
        return noOfUnitsPurchased / noOfUnitsInACarton;
    }

    public int calculateNofSingleUnitsToBePurchased(int noOfUnitsInACarton, int noOfUnitsPurchased) {
        return noOfUnitsPurchased % noOfUnitsInACarton;
    }
}
