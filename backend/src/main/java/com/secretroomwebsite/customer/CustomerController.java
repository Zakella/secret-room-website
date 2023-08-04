package com.secretroomwebsite.customer;

import com.secretroomwebsite.order.OrderReview;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("http://localhost:4200")
@RestController
@RequestMapping("/api/v1/customer")
public class CustomerController {

    @GetMapping("/orders")
    public ResponseEntity<String> getOrdersHistory() {
        return new ResponseEntity<>(
                   "This is a secured endpoint!",
                HttpStatus.OK);

    }
}
