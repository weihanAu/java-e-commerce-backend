package com.ecommerce.ecommerce.responsitory.Order;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ecommerce.ecommerce.model.Order;

public interface OrderResponsitory extends JpaRepository<Order,Long> {

}



