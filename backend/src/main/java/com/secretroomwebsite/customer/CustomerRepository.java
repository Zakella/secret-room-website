package com.secretroomwebsite.customer;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

    Customer findByPhone(String phone);

    Customer findByEmail(String email);
}
