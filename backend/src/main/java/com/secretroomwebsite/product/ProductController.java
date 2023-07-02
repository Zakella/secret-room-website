package com.secretroomwebsite.product;

import com.secretroomwebsite.productDTO.ProductDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/products")
@CrossOrigin("http://localhost:4200/")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/{categoryId}")
    public ResponseEntity<List<ProductDTO>> getAllProductsByCategory(@PathVariable("categoryId") Long categoryId) {

        List<ProductDTO> products = productService.getAllProductsByCategory(categoryId);
        if (products.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(products);
        }
    }

}
