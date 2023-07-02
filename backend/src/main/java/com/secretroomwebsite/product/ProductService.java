package com.secretroomwebsite.product;

import com.secretroomwebsite.enums.Brands;
import com.secretroomwebsite.productDTO.ProductDTO;
import lombok.Setter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    public Page<ProductDTO> getAllProductsByCategory(Long categoryId, Pageable pageable) {
        Page<Product> products = productRepository.findAllByProductCategory_Id(categoryId, pageable);

        return products.map(ProductDTO::fromProduct);

    }

    public Page<ProductDTO> getAllProductsVS(Pageable pageable) {
        Page<Product> products = productRepository.findByBrand(VictoriasSecret, pageable);
        return products.map(ProductDTO::fromProduct);
    }


    public Page<ProductDTO> getAllProductsBB(Pageable pageable) {
        Page<Product> products = productRepository.findByBrand(BathAndBody, pageable);
        return products.map(ProductDTO::fromProduct);
    }


//    public List<ProductDTO> getAllProductsBB(){
//
//        List<Product> products = productRepository.findByBrand(VictoriasSecret);
//
//        return products.stream()
//                .map(ProductDTO::fromProduct)
//                .collect(Collectors.toList());
//
//    }


}
