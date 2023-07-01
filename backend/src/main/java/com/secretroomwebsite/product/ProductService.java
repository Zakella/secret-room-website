package com.secretroomwebsite.product;

import lombok.Setter;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> getAllProductsByCategory(Long categoryId) {
        return productRepository.findAllByProductCategory_Id(categoryId);
    }
}
