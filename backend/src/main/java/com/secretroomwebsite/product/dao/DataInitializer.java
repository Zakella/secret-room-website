package com.secretroomwebsite.product.dao;;
import com.secretroomwebsite.enums.Brands;
import com.secretroomwebsite.product.category.ProductCategory;
import com.secretroomwebsite.product.category.ProductCategoryRepository;
import com.secretroomwebsite.shipping.ShippingRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import com.secretroomwebsite.product.Product;


import static com.secretroomwebsite.enums.Brands.BathAndBody;
import static com.secretroomwebsite.enums.Brands.VictoriasSecret;

@Component
public class DataInitializer implements CommandLineRunner {


    ProductCategoryRepository productCategoryRepository;

    ProductRepository productRepository;

    ShippingRepository shippingRepository;



    public DataInitializer(ProductCategoryRepository productCategoryRepository, ProductRepository productRepository, ShippingRepository shippingRepository) {
        this.productCategoryRepository = productCategoryRepository;
        this.productRepository = productRepository;
        this.shippingRepository = shippingRepository;
    }

    @Override

    public void run(String... args) throws Exception {

        ProductCategory panties = createCategory(
                "Panties",
                "Discover a wide range of women’s underwear at Victoria’s Secret.",
                "assets/demo-images/vs-groups/b1.jpg",
                VictoriasSecret);

        ProductCategory beauty  = createCategory(
                "Beauty",
                "Discover a wide range of women’s underwear at Victoria’s Secret.",
                "assets/demo-images/vs-groups/b1.jpg",
                VictoriasSecret);

        ProductCategory accessories  = createCategory(
                "Bags & Accessories",
                "Discover a wide range of women’s underwear at Victoria’s Secret.",
                "assets/demo-images/vs-groups/all1.jpg",
                VictoriasSecret);

        ProductCategory wellness  = createCategory(
                "Shop Wellness",
                "Center your best you with a little something new.",
                "https://cdn-fsly.yottaa.net/5d669b394f1bbf7cb77826ae/www.bathandbodyworks.com/v~4b.21a/on/demandware.static/-/Sites-BathAndBodyWorks-Library/default/dw5b7b79c7/images/Spring2023/xcat_wellness_sp3_hps.jpg?yocs=o_s_",
                BathAndBody);


        ProductCategory mens  = createCategory(
                "Shop Men's",
                "Now open: The Men’s Shop for all things head-to-toe you.",
                "https://cdn-fsly.yottaa.net/5d669b394f1bbf7cb77826ae/www.bathandbodyworks.com/v~4b.21a/on/demandware.static/-/Sites-BathAndBodyWorks-Library/default/dw6157884b/images/Summer2023/xcat_mensshop_su1_hps.jpg?yocs=o_s_",
                BathAndBody);



    }

    private ProductCategory createCategory(String categoryName, String description, String imageUrl, Brands brand) {

        ProductCategory category = new ProductCategory();
        category.setCategoryName(categoryName);
        category.setDescription(description);
        category.setImageUrl(imageUrl);
        category.setBrand(brand);

        return productCategoryRepository.save(category);

    }

    private Product createProduct(String sku,
                                  ProductCategory productCategory,
                                  String name, String description,
                                  Brands brand, String shortDescription,
                                  Double unitPrice,
                                  String imageURL,
                                  Boolean active,
                                  Integer unitsInStock) {

        Product product = new Product();
        product.setSku(sku);
        product.setProductCategory(productCategory);
        product.setName(name);
        product.setDescription(description);
        product.setBrand(brand);
        product.setShortDescription(shortDescription);
        product.setUnitPrice(unitPrice);
        product.setImageURL(imageURL);
        product.setActive(active);
        product.setUnitsInStock(unitsInStock);

        return productRepository.save(product);

    }






}
