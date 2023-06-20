package com.secretroomwebsite.product;

import com.secretroomwebsite.product.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.CrossOrigin;


public interface ProductRepository extends JpaRepository<Product, Long> {
}