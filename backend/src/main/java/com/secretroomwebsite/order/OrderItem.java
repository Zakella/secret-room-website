package com.secretroomwebsite.order;


import com.secretroomwebsite.product.Product;
import com.secretroomwebsite.enums.SizeType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.Objects;

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

    @Enumerated(EnumType.STRING)
    private SizeType sizeType;

    @Column(name = "amount")
    @NotNull(message = "Amount is mandatory")
    private Double amount;

    @Column(name = "quantity")
    @NotNull(message = "Quantity is mandatory")
    private Integer quantity;

    @ManyToOne
    @JoinColumn(name = "order_id")

    private Order order;

    public OrderItem(@NotNull Product product,
                     SizeType sizeType,
                     @NotNull Integer quantity,
                     @NotNull Double amount,
                     Order order) {

        this.product = product;
        this.sizeType = sizeType;
        this.quantity = quantity;
        this.amount = amount;
        this.order = order;

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderItem orderItem = (OrderItem) o;
        return Objects.equals(id, orderItem.id) && Objects.equals(product, orderItem.product) && sizeType == orderItem.sizeType && Objects.equals(amount, orderItem.amount) && Objects.equals(quantity, orderItem.quantity) && Objects.equals(order, orderItem.order);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, product, sizeType, amount, quantity, order);
    }


    // ... getters and setters ...
}
