package com.secretroomwebsite.product;

import com.secretroomwebsite.enums.Brands;
import com.secretroomwebsite.productDTO.ProductDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.secretroomwebsite.enums.Brands.BathAndBody;
import static com.secretroomwebsite.enums.Brands.VictoriasSecret;

@RestController
@RequestMapping("/api/v1/products")
@CrossOrigin("http://localhost:4200/")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/{categoryId}")
    public ResponseEntity<Page<ProductDTO>> getAllProductsByCategory(@PathVariable("categoryId") Long categoryId, Pageable pageable) {
        return buildResponse(productService.getAllProductsByCategory(categoryId, pageable));
    }

    @GetMapping("/vs")
    public ResponseEntity<Page<ProductDTO>> getAllProductsVS(Pageable pageable) {
        return buildResponse(productService.getAllProductsVS(pageable));
//        return buildResponse(products);
    }
    @GetMapping("/bb")
    public ResponseEntity<Page<ProductDTO>> getAllProductsBB(Pageable pageable) {
        return buildResponse(productService.getAllProductsBB(pageable));
    }

    private ResponseEntity<Page<ProductDTO>> buildResponse(Page<ProductDTO> products) {
        if (products.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(products);
        }
    }

    @GetMapping("vs/searchByNameContaining")
    public ResponseEntity<Page<ProductDTO>> findByNameVS(@RequestParam("name") String name, Pageable pageable) {
        return buildResponse(productService.findByNameContaining(name, VictoriasSecret, pageable));
    }

    @GetMapping("bb/searchByNameContaining")
    public ResponseEntity<Page<ProductDTO>> findByNameBB(@RequestParam("name") String name, Pageable pageable) {
        return buildResponse(productService.findByNameContaining(name, BathAndBody, pageable));
    }
}
