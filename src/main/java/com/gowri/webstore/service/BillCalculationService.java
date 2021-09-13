package com.gowri.webstore.service;

import com.gowri.webstore.domain.Product;
import com.gowri.webstore.domain.Purchase;
import com.gowri.webstore.service.discounts.impl.FixDiscount;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
public class BillCalculationService {

    private final PriceEngineOptimizationService priceEngineOptimizationService;
    private final FixDiscount fixDiscount;
    private final double labourCompensatePercentage = 0.3;

    public BillCalculationService(PriceEngineOptimizationService priceEngineOptimizationService, 
                                  FixDiscount fixDiscount) {
        this.priceEngineOptimizationService = priceEngineOptimizationService;
        this.fixDiscount = fixDiscount;
    }

    public void calculateBill(Purchase purchase) {
        purchase.getItems().forEach(this::calculateNoOfCartonsAndSingleUnits);
        calculateTotal(purchase);
        fixDiscount.apply(purchase);
    }

    public Purchase.Item calculateNoOfCartonsAndSingleUnits(Purchase.Item purchaseItem) {
        Product product = purchaseItem.getProduct();
        int nofPurchasableCartons = priceEngineOptimizationService
                .calculateNofPurchasableCartons(product.getNoOfUnitsInCarton(), purchaseItem.getTotalNoOfSingleUnits());
        purchaseItem.setNoOfCartons(nofPurchasableCartons);
        int nofSingleUnits = priceEngineOptimizationService
                .calculateNofSingleUnitsToBePurchased(product.getNoOfUnitsInCarton(), purchaseItem.getTotalNoOfSingleUnits());
        purchaseItem.setNoOfSingleUnits(nofSingleUnits);
        BigDecimal totalCartonPrice = product.getPricePerCarton()
                .multiply(BigDecimal.valueOf(nofPurchasableCartons));
        BigDecimal singleUnitsPrice = calculateUnitPrice(product.getPricePerCarton(), product.getNoOfUnitsInCarton())
                .multiply(BigDecimal.valueOf(nofSingleUnits));
        BigDecimal totalSingleUnitPrice = singleUnitsPrice.multiply(BigDecimal.valueOf(nofSingleUnits));
        if(nofSingleUnits > 0) {
            BigDecimal labourCompensationAmount = product.getPricePerCarton()
                    .multiply(BigDecimal.valueOf(labourCompensatePercentage));
            totalSingleUnitPrice = totalSingleUnitPrice.add(labourCompensationAmount);
        }
        purchaseItem.setCartonPrice(totalCartonPrice);
        purchaseItem.setSingleUnitsPrice(totalSingleUnitPrice);
        return purchaseItem;
    }

    public BigDecimal calculateUnitPrice(BigDecimal priceOfCarton, int noOfUnitsInACarton) {
        return priceOfCarton.divide(BigDecimal.valueOf(noOfUnitsInACarton));
    }

    public void calculateTotal(Purchase purchase) {
        BigDecimal total = purchase.getItems().stream().map(item -> item.getCartonPrice()
                .add(item.getSingleUnitsPrice()))
                .reduce(BigDecimal.ZERO, BigDecimal::add)
                .setScale(2, RoundingMode.HALF_UP);
        purchase.setTotal(total);
    }





}
