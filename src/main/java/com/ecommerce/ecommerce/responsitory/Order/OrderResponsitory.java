package com.ecommerce.ecommerce.responsitory.Order;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ecommerce.ecommerce.model.Order;

public interface OrderResponsitory extends JpaRepository<Order,Long> {
   List<Order> findByUserId(Long userId);
}



