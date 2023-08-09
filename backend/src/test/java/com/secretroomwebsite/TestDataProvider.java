package com.secretroomwebsite;

import com.secretroomwebsite.purchase.Purchase;
import com.secretroomwebsite.order.Order;
import com.secretroomwebsite.order.OrderItem;
import com.secretroomwebsite.product.Product;
import com.secretroomwebsite.product.category.ProductCategory;
import com.secretroomwebsite.product.category.ProductCategoryRepository;
import com.secretroomwebsite.product.dao.ProductRepository;
import com.secretroomwebsite.shipping.Shipping;
import com.secretroomwebsite.shipping.ShippingRepository;

import java.util.List;

import static com.secretroomwebsite.TestData.*;
import static com.secretroomwebsite.enums.SizeType.S;


public class TestDataProvider {

    private final ShippingRepository shippingRepository;


    private final ProductRepository productRepository;


    private final ProductCategoryRepository productCategoryRepository;

    public TestDataProvider(ShippingRepository shippingRepository, ProductRepository productRepository, ProductCategoryRepository productCategoryRepository) {
        this.shippingRepository = shippingRepository;
        this.productRepository = productRepository;
        this.productCategoryRepository = productCategoryRepository;
    }

    public Purchase preparePurchaseForTest(){

        Shipping shipping = shippingRepository.save(getTestShipping());

        ProductCategory savedCategory = productCategoryRepository.save(getTestProductCategory());
        Product product1 = getProduct1();
        product1.setProductCategory(savedCategory);

        Product product2 = getProduct2();
        product2.setProductCategory(savedCategory);

        Product savedProduct1 = productRepository.save(product1);
        Product savedProduct2 = productRepository.save(product2);

        Order order = getTestOrder();
        order.setShippingOption(shipping);


        Purchase purchase = getTestPurchase();
        purchase.setOrderItems(
                List.of(
                        new OrderItem(savedProduct1, S, 5, 50.00, null),
                        new OrderItem(savedProduct2, null, 5, 100.00, null))
        );

        purchase.setCustomer(getTestCustomer());
        purchase.setOrder(order);
        return purchase;


    }
}


