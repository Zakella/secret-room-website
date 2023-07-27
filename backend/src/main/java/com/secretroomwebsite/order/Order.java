package com.secretroomwebsite.order;

import com.secretroomwebsite.adress.Address;
import com.secretroomwebsite.customer.Customer;
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

    @Column(name="order_tracking_number")
    private String orderTrackingNumber;

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

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "shipping_address_id", referencedColumnName = "id")
    private Address shippingAddress;

    String comment;

    public Order() {}


}
