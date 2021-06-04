package com.wly.ecommerce.service;

import com.wly.ecommerce.dto.Purchase;
import com.wly.ecommerce.dto.PurchaseResponse;

public interface CheckoutService {

    PurchaseResponse placeOrder(Purchase purchase);
}
