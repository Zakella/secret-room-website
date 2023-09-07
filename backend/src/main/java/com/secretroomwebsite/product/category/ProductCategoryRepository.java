package com.secretroomwebsite.product.category;

import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository

public interface ProductCategoryRepository extends JpaRepository<ProductCategory, Long> {

    @NotNull List<ProductCategory> findAll();

    @NotNull Optional<ProductCategory> findById(@NotNull Long id);

}