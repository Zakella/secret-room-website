package com.secretroomwebsite.productDTO;

import com.secretroomwebsite.enums.Brands;
import com.secretroomwebsite.product.Product;
import com.secretroomwebsite.productSizes.SizeType;
import com.secretroomwebsite.product_category.ProductCategory;
import com.secretroomwebsite.productImages.ProductImage;
import com.secretroomwebsite.productSizes.Size;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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

        List<ProductImage> images = new ArrayList<>();
        ProductImage image1 = new ProductImage();
        image1.setImageUrl("https://example.com/image1.jpg");
        ProductImage image2 = new ProductImage();
        image2.setImageUrl("https://example.com/image2.jpg");
        images.add(image1);
        images.add(image2);
        product.setImages(images);

        List<Size> sizes = new ArrayList<>();
        Size size1 = new Size();
        size1.setSizeType(SizeType.S);
        size1.setAvailable(true);
        Size size2 = new Size();
        size2.setSizeType(SizeType.M);
        size2.setAvailable(true);
        sizes.add(size1);
        sizes.add(size2);
        product.setSizes(sizes);

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
        Assertions.assertEquals(2, productDTO.getProductImages().size());
        Assertions.assertEquals(2, productDTO.getProductSizes().size());
    }
}
