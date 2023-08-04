package com.secretroomwebsite.product.category;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/product-category")
public class ProductCategoryController {

    public ProductGroupService productGroupService;

    public ProductCategoryController(ProductGroupService productGroupService) {
        this.productGroupService = productGroupService;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ProductCategory> getAllProductCategories() {
        return productGroupService.getAllCategories();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getProductCategoryById(@PathVariable Long id) {
        return productGroupService.getProductCategoryById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }


}
