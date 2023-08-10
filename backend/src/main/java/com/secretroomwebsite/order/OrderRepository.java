package com.secretroomwebsite.order;

import org.aspectj.weaver.ast.Or;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    Optional<Order> findByOrderTrackingNumber(String uuid);
    List<Order> findByCustomer_EmailOrderByPlacementDateDesc(String email);
}
