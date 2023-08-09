package com.secretroomwebsite.checkout.purchase;

import com.secretroomwebsite.purchase.Purchase;
import com.secretroomwebsite.purchase.PurchaseResponse;

public interface CheckoutService {

    PurchaseResponse placeOrder(Purchase purchase);
}
