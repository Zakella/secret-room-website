package com.secretroomwebsite.order;

import com.secretroomwebsite.shipping.Shipping;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.proxy.HibernateProxy;

import java.util.Date;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "orders")
@Getter
@Setter
@NoArgsConstructor
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "placement_date")
    @CreationTimestamp
    private Date placementDate;

    @Column(name = "first_name")
    @NotBlank
    @Pattern(regexp="^[A-Za-z\\s]*$", message="Name should be valid")
    private String firstName;

    @Column(name = "last_name")
    @NotBlank
    @Pattern(regexp="^[A-Za-z\\s]*$", message="Name should be valid")
    private String lastName;

    @Column(name = "email")
    @Pattern(regexp="^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$", message="Email should be valid")
    private String email;

    @Column(name = "phone_number")
    @Pattern(regexp="^(06|07)\\d{7}$", message="Phone should be valid")
    @NotBlank
    private String phoneNumber;

    @Column(name = "delivery_address")
    @NotBlank
    private String deliveryAddress;

    @ManyToOne
    @JoinColumn(name = "shipping_option_id")
    private Shipping shippingOption;

    @Column(name = "total_quantity")
    private Integer totalQuantity;

    @Column(name = "total_amount")
    private Double totalAmount;

    @Column(name = "total_amount_order")
    private Double totalAmountOrder;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    @NotEmpty
    @ToString.Exclude
    private List<OrderItem> items;

    // ... getters and setters ...

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        Order order = (Order) o;
        return getId() != null && Objects.equals(getId(), order.getId());
    }

    @Override
    public final int hashCode() {
        return getClass().hashCode();
    }
}
