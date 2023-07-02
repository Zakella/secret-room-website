package com.secretroomwebsite.productDTO;
import com.secretroomwebsite.product.Product;
import com.secretroomwebsite.product_category.ProductCategory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static com.secretroomwebsite.enums.Brands.VictoriasSecret;

public class ProductDTOTest {
    @Test
    public void testFromProduct() {

        Product product = new Product();
        product.setId(1L);
        product.setSku("SKU123");
        product.setName("Test Product");
        product.setDescription("This is a test product");
        product.setBrand(VictoriasSecret);
        product.setShortDescription("Test");
        product.setUnitPrice(9.99);
        product.setImageURL("https://example.com/image.jpg");
        product.setActive(true);
        product.setUnitsInStock(10);
        product.setDateCreated(LocalDate.of(2022, 1, 1));
        product.setLastUpdated(LocalDate.of(2022, 2, 1));
        ProductCategory category = new ProductCategory();
        category.setCategoryName("Test Category");
        product.setProductCategory(category);


        ProductDTO productDTO = ProductDTO.fromProduct(product);


        Assertions.assertEquals(1L, productDTO.getId());
        Assertions.assertEquals("SKU123", productDTO.getSku());
        Assertions.assertEquals("Test Category", productDTO.getCategoryName());
        Assertions.assertEquals("Test Product", productDTO.getName());
        Assertions.assertEquals("This is a test product", productDTO.getDescription());
        Assertions.assertEquals(VictoriasSecret, productDTO.getBrand());
        Assertions.assertEquals("Test", productDTO.getShortDescription());
        Assertions.assertEquals(9.99, productDTO.getUnitPrice());
        Assertions.assertEquals("https://example.com/image.jpg", productDTO.getImageURL());
        Assertions.assertTrue(productDTO.getActive());
        Assertions.assertEquals(10, productDTO.getUnitsInStock());
        Assertions.assertEquals(LocalDate.of(2022, 1, 1), productDTO.getDateCreated());
        Assertions.assertEquals(LocalDate.of(2022, 2, 1), productDTO.getLastUpdated());
    }
}
