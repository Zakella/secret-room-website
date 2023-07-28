package com.secretroomwebsite.checkout.purchase;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("http://localhost:4200")
@RestController
@RequestMapping("/api/v1/checkout")
public class CheckoutController {

    private final CheckoutService checkoutService;

    public CheckoutController(CheckoutService checkoutService) {
        this.checkoutService = checkoutService;
    }


    @PostMapping
    public ResponseEntity<PurchaseResponse> placeOrder(@RequestBody Purchase purchase) {
        PurchaseResponse response   = checkoutService.placeOrder(purchase);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}









