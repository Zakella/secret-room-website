package com.secretroomwebsite.product;

import com.secretroomwebsite.enums.Brands;
import com.secretroomwebsite.productDTO.ProductDTO;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.secretroomwebsite.enums.Brands.BathAndBody;
import static com.secretroomwebsite.enums.Brands.VictoriasSecret;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<ProductDTO> getAllProductsByCategory(Long categoryId) {
        List<Product> products = productRepository.findAllByProductCategory_Id(categoryId);

        return products.stream()
                .map(ProductDTO::fromProduct)
                .collect(Collectors.toList());
    }

    public List<ProductDTO> getAllProductsVS(){
        List<Product> products = productRepository.findByBrand(VictoriasSecret);

        return products.stream()
                .map(ProductDTO::fromProduct)
                .collect(Collectors.toList());
    }

    public List<ProductDTO> getAllProductsBB(){

        List<Product> products = productRepository.findByBrand(BathAndBody);

        return products.stream()
                .map(ProductDTO::fromProduct)
                .collect(Collectors.toList());

    }


}
