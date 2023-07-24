package com.secretroomwebsite.order.items;


import com.secretroomwebsite.order.Order;
import com.secretroomwebsite.product.Product;
import com.secretroomwebsite.productSizes.Size;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OrderItem orderItem = (OrderItem) o;

        if (!Objects.equals(id, orderItem.id)) return false;
        if (!product.equals(orderItem.product)) return false;
        if (!Objects.equals(size, orderItem.size)) return false;
        if (!amount.equals(orderItem.amount)) return false;
        if (!quantity.equals(orderItem.quantity)) return false;
        return Objects.equals(order, orderItem.order);
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + product.hashCode();
        result = 31 * result + (size != null ? size.hashCode() : 0);
        result = 31 * result + amount.hashCode();
        result = 31 * result + quantity.hashCode();
        result = 31 * result + (order != null ? order.hashCode() : 0);
        return result;
    }

    // ... getters and setters ...
}
