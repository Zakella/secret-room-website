package com.secretroomwebsite.order;


import com.secretroomwebsite.product.Product;
import com.secretroomwebsite.productSizes.Size;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Entity
@Table(name = "order_items")
@Getter
@Setter
@NoArgsConstructor
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "product_id")
    @NotNull
    private Product product;

    @ManyToOne
    @JoinColumn(name = "size_id")
    private Size size;

    @Column(name = "amount")
    @NotNull(message = "Amount is mandatory")
    private Double amount;

    @Column(name = "quantity")
    @NotNull(message = "Quantity is mandatory")
    private Integer quantity;

    @ManyToOne
    @JoinColumn(name = "order_id")

    private Order order;

    public OrderItem(Product product,
                     Size size,
                     Integer quantity,
                     Double amount,
                     Order order) {

        this.product = product;
        this.size = size;
        this.quantity = quantity;
        this.amount = amount;
        this.order = order;

    }

    // ... getters and setters ...
}
