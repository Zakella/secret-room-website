package com.secretroomwebsite.product;

import com.secretroomwebsite.enums.Brands;
import com.secretroomwebsite.exception.ResourceNotFoundException;
import com.secretroomwebsite.product.dao.ProductRepository;
import com.secretroomwebsite.product.dto.ProductDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

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


    public Page<ProductDTO> findByNameContaining( String name, Brands brand, Pageable pageable) {
//        if (language.equals("ro")){
//            Page<Product> products = productRepository.findByNameContainingIgnoreCaseAndBrand(name,  brand,  pageable);
//            return products.map(ProductDTO::fromProduct);
//        }
//        else {
//            Page<Product> products = productRepository.findByNameContainingIgnoreCaseAndBrand(name,  brand,  pageable);
//            return products.map(ProductDTO::fromProduct);
//        }
        Page<Product> products = productRepository.findByNameContainingIgnoreCaseAndBrand(name,  brand,  pageable);
        return products.map(ProductDTO::fromProduct);
    }

    public ProductDTO findProductById(Long id ){

        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Product with id %d not found!", id)));

      return ProductDTO.fromProduct(product);

    }


}
