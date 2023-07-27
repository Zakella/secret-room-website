package com.secretroomwebsite.order;

import com.secretroomwebsite.order.items.OrderItem;
import com.secretroomwebsite.shipping.Shipping;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "orders")
@Getter
@Setter
@AllArgsConstructor
@ToString

public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private OrderStatus status;


    @CreationTimestamp
    private Date placementDate;


    @NotBlank
    @Pattern(regexp="^[A-Za-z\\s]*$", message="Name should be valid")
    private String firstName;


    @NotBlank
    @Pattern(regexp="^[A-Za-z\\s]*$", message="Name should be valid")
    private String lastName;


    @Pattern(regexp="^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$", message="Email should be valid")
    private String email;


    @Pattern(regexp="^(06|07)\\d{7}$", message="Phone should be valid")
    @NotBlank
    private String phoneNumber;


    @NotBlank
    private String deliveryAddress;

    @ManyToOne
    @JoinColumn(name = "shipping_option_id")
    private Shipping shippingOption;

    @NotNull
    private Integer totalQuantity;

    @NotNull
    private Double totalAmount;

    @NotNull
    private Double shippingCost;

    @NotNull
    private Double totalAmountOrder;



    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    @NotEmpty
    @ToString.Exclude
    private List<OrderItem> items;

    public Order() {
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return Objects.equals(id, order.id) && status == order.status && Objects.equals(placementDate, order.placementDate) && Objects.equals(firstName, order.firstName) && Objects.equals(lastName, order.lastName) && Objects.equals(email, order.email) && Objects.equals(phoneNumber, order.phoneNumber) && Objects.equals(deliveryAddress, order.deliveryAddress) && Objects.equals(shippingOption, order.shippingOption) && Objects.equals(totalQuantity, order.totalQuantity) && Objects.equals(totalAmount, order.totalAmount) && Objects.equals(shippingCost, order.shippingCost) && Objects.equals(totalAmountOrder, order.totalAmountOrder) && Objects.equals(items, order.items);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, status, placementDate, firstName, lastName, email, phoneNumber, deliveryAddress, shippingOption, totalQuantity, totalAmount, shippingCost, totalAmountOrder, items);
    }
}
