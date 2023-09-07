package com.secretroomwebsite.product.category;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class ProductGroupService {

    private ProductCategoryRepository categoryRepository;

    public ProductGroupService(ProductCategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public List<ProductCategory> getAllCategories() {
        return categoryRepository.findAll();
    }

    public Optional<ProductCategory> getProductCategoryById(Long id) {
        return categoryRepository.findById(id);
    }


}
