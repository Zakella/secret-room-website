package com.secretroomwebsite.product;

import com.secretroomwebsite.enums.Brands;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.CrossOrigin;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;


@CrossOrigin("http://localhost:4200/")
@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    Page<Product> findAllByProductCategory_Id(Long categoryId, Pageable pageable);

    Page<Product> findByBrand(Brands brand, Pageable pageable);

    Page<Product> findByNameContainingIgnoreCaseAndBrand (String name, Brands brands , Pageable pageable);

}

