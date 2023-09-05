package com.secretroomwebsite.product.dto;

import com.secretroomwebsite.enums.Brands;
import com.secretroomwebsite.product.Product;
import com.secretroomwebsite.product.category.ProductCategory;
import com.secretroomwebsite.product.image.ProductImage;
import com.secretroomwebsite.product.sizes.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO {
    private Long id;
    private String sku;
    private String nameRo;
    private String nameRu;
    private String descriptionRo;
    private String descriptionRu;
    private String shortDescriptionRo;
    private String shortDescriptionRu;
    private ProductCategory category;
    private Brands brand;
    private Double unitPrice;
    private String imageURL;
    private Boolean active;
    private Integer unitsInStock;
    private LocalDate dateCreated;
    private LocalDate lastUpdated;
    private List<ProductImage> productImages;
    private List<Size> productSizes;
    private String brandAlias;
    private String brandShortName;


    public static ProductDTO fromProduct(Product product) {
        List<String> imageUrls = product.getImages().stream()
                .map((ProductImage::getImageUrl))
                .toList();

        return ProductDTO.builder()
                .id(product.getId())
                .sku(product.getSku())
                .nameRo(product.getNameRo())
                .nameRu(product.getNameRu())
                .descriptionRo(product.getDescriptionRo())
                .descriptionRu(product.getDescriptionRu())
                .shortDescriptionRo(product.getShortDescriptionRo())
                .shortDescriptionRu(product.getShortDescriptionRu())
                .category(product.getProductCategory())
                .brand(product.getBrand())
                .unitPrice(product.getUnitPrice())
                .imageURL(product.getImageURL())
                .active(product.getActive())
                .unitsInStock(product.getUnitsInStock())
                .dateCreated(product.getDateCreated())
                .lastUpdated(product.getLastUpdated())
                .productImages(product.getImages())
                .productSizes(product.getSizes())
                .brandAlias(product.getBrand().getName())
                .brandShortName(getBrandShortName(product.getBrand()))
                .build();
    }

    private static String getBrandShortName(Brands brand) {
        if (Objects.requireNonNull(brand) == Brands.BathAndBody) {
            return "bb";
        }
        return "vs";
    }
}
