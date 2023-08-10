package com.secretroomwebsite.user;

import java.util.List;
import com.secretroomwebsite.order.Order;

public record UserAccountInfo(List<Order> orders) {}