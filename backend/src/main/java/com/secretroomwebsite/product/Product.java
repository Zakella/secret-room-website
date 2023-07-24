package com.secretroomwebsite.product;

import com.fasterxml.jackson.annotation.*;
import com.secretroomwebsite.enums.Brands;
import com.secretroomwebsite.productImages.ProductImage;
import com.secretroomwebsite.productSizes.Size;
import com.secretroomwebsite.product_category.ProductCategory;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.proxy.HibernateProxy;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "product")
@Builder
@AllArgsConstructor
@Getter
@Setter
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String sku;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    @JsonBackReference
    @JsonIgnoreProperties("products")
    private ProductCategory productCategory;

    private String name;

    private String description;

    @Enumerated(EnumType.STRING)
    private Brands brand;


    private String shortDescription;

    @Column(name = "unit_price")
    private Double unitPrice;

    @Column(name = "image_url")
    private String imageURL;
    private Boolean active;

    @Column(name = "units_in_stock")
    private Integer unitsInStock;

    @Column(name = "date_created")
    @CreationTimestamp
    private LocalDate dateCreated;

    @Column(name = "last_updated")
    @UpdateTimestamp
    @JsonIgnore
    private LocalDate lastUpdated;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    @JsonProperty("productImages")
    @ToString.Exclude
    private List<ProductImage> images ;


    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    @ToString.Exclude
    private List<Size> sizes;

    public Product() {
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Product product = (Product) o;

        if (!Objects.equals(id, product.id)) return false;
        if (!Objects.equals(sku, product.sku)) return false;
        if (!Objects.equals(productCategory, product.productCategory))
            return false;
        if (!Objects.equals(name, product.name)) return false;
        if (!Objects.equals(description, product.description)) return false;
        if (brand != product.brand) return false;
        if (!Objects.equals(shortDescription, product.shortDescription))
            return false;
        if (!Objects.equals(unitPrice, product.unitPrice)) return false;
        if (!Objects.equals(imageURL, product.imageURL)) return false;
        if (!Objects.equals(active, product.active)) return false;
        if (!Objects.equals(unitsInStock, product.unitsInStock))
            return false;
        if (!Objects.equals(dateCreated, product.dateCreated)) return false;
        if (!Objects.equals(lastUpdated, product.lastUpdated)) return false;
        if (!Objects.equals(images, product.images)) return false;
        return Objects.equals(sizes, product.sizes);
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (sku != null ? sku.hashCode() : 0);
        result = 31 * result + (productCategory != null ? productCategory.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (brand != null ? brand.hashCode() : 0);
        result = 31 * result + (shortDescription != null ? shortDescription.hashCode() : 0);
        result = 31 * result + (unitPrice != null ? unitPrice.hashCode() : 0);
        result = 31 * result + (imageURL != null ? imageURL.hashCode() : 0);
        result = 31 * result + (active != null ? active.hashCode() : 0);
        result = 31 * result + (unitsInStock != null ? unitsInStock.hashCode() : 0);
        result = 31 * result + (dateCreated != null ? dateCreated.hashCode() : 0);
        result = 31 * result + (lastUpdated != null ? lastUpdated.hashCode() : 0);
        result = 31 * result + (images != null ? images.hashCode() : 0);
        result = 31 * result + (sizes != null ? sizes.hashCode() : 0);
        return result;
    }


}
