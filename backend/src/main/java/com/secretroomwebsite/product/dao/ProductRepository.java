package com.secretroomwebsite.product.dao;

import com.secretroomwebsite.enums.Brands;
import com.secretroomwebsite.product.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.CrossOrigin;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;


@CrossOrigin("http://localhost:4200/")
@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    Page<Product> findAllByProductCategory_Id(Long categoryId, Pageable pageable);

    Page<Product> findByBrand(Brands brand, Pageable pageable);

    Page<Product> findByNameContainingIgnoreCaseAndBrand (String name, Brands brands , Pageable pageable);

    Optional<Product> findById(Long id);

}

