package com.secretroomwebsite.product;

import com.secretroomwebsite.product.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.List;


@CrossOrigin("http://localhost:4200/")
public interface ProductRepository extends JpaRepository<Product, Long> {

//    List<Product> findAllByProductCategory(Long id);

    List<Product> findAllByProductCategory_Id(Long categoryId);

}