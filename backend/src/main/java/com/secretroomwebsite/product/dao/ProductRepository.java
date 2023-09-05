package com.secretroomwebsite.product.dao;

import com.secretroomwebsite.enums.Brands;
import com.secretroomwebsite.product.Product;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.CrossOrigin;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;



@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    Page<Product> findAllByProductCategory_Id(Long categoryId, Pageable pageable);

    Page<Product> findByBrand(Brands brand, Pageable pageable);

    @Query("SELECT p FROM Product p WHERE (LOWER(p.nameRu) LIKE LOWER(CONCAT('%',:name,'%')) OR LOWER(p.nameRo) LIKE LOWER(CONCAT('%',:name,'%'))) AND p.brand = :brand")
    Page<Product> findByNameContainingIgnoreCaseAndBrand(String name, Brands brand, Pageable pageable);

    @NotNull Optional<Product> findById(@NotNull Long id);

}

