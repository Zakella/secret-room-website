package com.secretroomwebsite.customer;

import com.secretroomwebsite.order.Order;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "customers")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = "orders")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Pattern(regexp="^[A-Za-z\\s]*$", message="Name should be valid")
    private String firstName;

    @NotBlank
    @Pattern(regexp="^[A-Za-z\\s]*$", message="LastName should be valid")
    private String lastName;

    @Pattern(regexp="^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$", message="Email should be valid")
    private String email;

    @Pattern(regexp="^(06|07)\\d{7}$", message="Phone should be valid")
    @NotBlank
    @Column(unique = true)  // Ensure phone number is unique across all customers
    private String phone;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
    private Set<Order> orders = new HashSet<>();
}
