package com.ecommerce.ecommerce.service.order;

import java.util.List;

import com.ecommerce.ecommerce.dto.OrderDto;

import com.ecommerce.ecommerce.model.Order;

public interface IOrderService {
    Order placeOrder(Long userId);
    OrderDto getOrder(Long orderId);
    List<OrderDto> getUserOrders(Long userId);
    OrderDto convertOrderDto(Order order);
}
