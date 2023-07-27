package com.secretroomwebsite.checkout.purchase;

public interface CheckoutService {

    PurchaseResponse placeOrder(Purchase purchase);
}
