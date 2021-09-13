package com.gowri.webstore.controller;

import com.gowri.webstore.domain.Purchase;
import com.gowri.webstore.domain.PurchaseRequest;
import com.gowri.webstore.service.PurchaseService;
import org.springframework.web.bind.annotation.*;

@RestController
public class PurchaseController {

    private final PurchaseService purchaseService;

    public PurchaseController(PurchaseService purchaseService) {
        this.purchaseService = purchaseService;
    }

    @RequestMapping(value = "/checkbill", method = RequestMethod.POST)
    public Purchase checkBill(@RequestBody PurchaseRequest request) {
          return purchaseService.checkBill(request);
    }


}
