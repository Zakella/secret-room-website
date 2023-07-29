package com.secretroomwebsite.order;
import com.secretroomwebsite.checkout.purchase.CheckoutService;
import com.secretroomwebsite.checkout.purchase.Purchase;
import com.secretroomwebsite.checkout.purchase.PurchaseResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("http://localhost:4200")
@RestController
@RequestMapping("/api/v1/order")
public class OrderController {

    private final CheckoutService checkoutService;

    private final OrderService orderService;

    public OrderController(CheckoutService checkoutService,
                           OrderService orderService) {
        this.checkoutService = checkoutService;
        this.orderService = orderService;
    }


    @PostMapping
    public ResponseEntity<PurchaseResponse> placeOrder(@RequestBody Purchase purchase) {
        PurchaseResponse response   = checkoutService.placeOrder(purchase);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<OrderReview> placeOrder(@PathVariable("uuid") String uuid) {
        return new ResponseEntity<>(
                orderService.findOrderByTrackingNumber(uuid),
                HttpStatus.OK);
    }
}









