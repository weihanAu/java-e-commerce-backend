package com.ecommerce.ecommerce.controller;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.ecommerce.dto.OrderDto;
import com.ecommerce.ecommerce.model.Order;
import com.ecommerce.ecommerce.response.ApiResponse;
import com.ecommerce.ecommerce.service.order.IOrderService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/orders")
public class OrderController {
  private final IOrderService orderService;

  @PostMapping("/order")
  public ResponseEntity<ApiResponse> createOrder(@RequestParam Long userId){
    try {
      Order order = orderService.placeOrder(userId);
      OrderDto orderDto = orderService.convertOrderDto(order);
      return ResponseEntity.ok(new ApiResponse("item order success!", orderDto));
    } catch (Exception e) {
      return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(),INTERNAL_SERVER_ERROR));
    }
  }

  @GetMapping("/{orderId}/order")
  public ResponseEntity<ApiResponse> getOrderbyId(@PathVariable Long orderId){
    try {
      OrderDto order = orderService.getOrder(orderId);
      return ResponseEntity.ok(new ApiResponse("item order success!", order));
    } catch (Exception e) {
      return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(),NOT_FOUND));
    }
  }

  
  @GetMapping("/{userId}/orders")
  public ResponseEntity<ApiResponse> getUserOrders(@PathVariable Long userId){
    try {
      List<OrderDto> orders = orderService.getUserOrders(userId);
      return ResponseEntity.ok(new ApiResponse("item order success!", orders));
    } catch (Exception e) {
      return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(),NOT_FOUND));
    }
  }

}
