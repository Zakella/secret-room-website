    package com.secretroomwebsite.product_category;

    import com.secretroomwebsite.product.Product;
    import jakarta.persistence.*;
    import lombok.Getter;
    import lombok.Setter;

    import java.util.Set;

    @Entity
    @Getter
    @Setter
    @Table(name = "product_category")
    public class ProductCategory {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @Column(name = "category_name")
        private String categoryName;


        @OneToMany(mappedBy = "productCategory", cascade = CascadeType.ALL)
        private Set<Product> products;

    }
