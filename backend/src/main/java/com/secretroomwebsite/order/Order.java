package com.secretroomwebsite.order;

import com.secretroomwebsite.order.items.OrderItem;
import com.secretroomwebsite.shipping.Shipping;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
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

public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private OrderStatus status;

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

    public Order() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Order order = (Order) o;

        if (!Objects.equals(id, order.id)) return false;
        if (status != order.status) return false;
        if (!Objects.equals(placementDate, order.placementDate))
            return false;
        if (!Objects.equals(firstName, order.firstName)) return false;
        if (!Objects.equals(lastName, order.lastName)) return false;
        if (!Objects.equals(email, order.email)) return false;
        if (!Objects.equals(phoneNumber, order.phoneNumber)) return false;
        if (!Objects.equals(deliveryAddress, order.deliveryAddress))
            return false;
        if (!Objects.equals(shippingOption, order.shippingOption))
            return false;
        if (!Objects.equals(totalQuantity, order.totalQuantity))
            return false;
        if (!Objects.equals(totalAmount, order.totalAmount)) return false;
        if (!Objects.equals(totalAmountOrder, order.totalAmountOrder))
            return false;
        return Objects.equals(items, order.items);
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + status.hashCode();
        result = 31 * result + (placementDate != null ? placementDate.hashCode() : 0);
        result = 31 * result + (firstName != null ? firstName.hashCode() : 0);
        result = 31 * result + (lastName != null ? lastName.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (phoneNumber != null ? phoneNumber.hashCode() : 0);
        result = 31 * result + (deliveryAddress != null ? deliveryAddress.hashCode() : 0);
        result = 31 * result + (shippingOption != null ? shippingOption.hashCode() : 0);
        result = 31 * result + (totalQuantity != null ? totalQuantity.hashCode() : 0);
        result = 31 * result + (totalAmount != null ? totalAmount.hashCode() : 0);
        result = 31 * result + (totalAmountOrder != null ? totalAmountOrder.hashCode() : 0);
        result = 31 * result + (items != null ? items.hashCode() : 0);
        return result;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public Date getPlacementDate() {
        return placementDate;
    }

    public void setPlacementDate(Date placementDate) {
        this.placementDate = placementDate;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getDeliveryAddress() {
        return deliveryAddress;
    }

    public void setDeliveryAddress(String deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }

    public Shipping getShippingOption() {
        return shippingOption;
    }

    public void setShippingOption(Shipping shippingOption) {
        this.shippingOption = shippingOption;
    }

    public Integer getTotalQuantity() {
        return totalQuantity;
    }

    public void setTotalQuantity(Integer totalQuantity) {
        this.totalQuantity = totalQuantity;
    }

    public Double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Double getTotalAmountOrder() {
        return totalAmountOrder;
    }

    public void setTotalAmountOrder(Double totalAmountOrder) {
        this.totalAmountOrder = totalAmountOrder;
    }

    public List<OrderItem> getItems() {
        return items;
    }

    public void setItems(List<OrderItem> items) {
        this.items = items;
    }
}
