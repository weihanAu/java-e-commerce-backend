package com.ecommerce.ecommerce.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import static org.springframework.http.HttpStatus.NOT_FOUND;

import java.math.BigDecimal;

import com.ecommerce.ecommerce.exception.ResourceNotFoundException;
import com.ecommerce.ecommerce.model.Cart;
import com.ecommerce.ecommerce.response.ApiResponse;
import com.ecommerce.ecommerce.service.cart.ICartService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/carts")
public class CartController {
  private final ICartService cartService;

  @GetMapping("/{cartId}/my-cart")
  public ResponseEntity<ApiResponse> getCart(@PathVariable Long cartId){
    try {
      Cart cart = cartService.getCart(cartId);
      return ResponseEntity.ok(new ApiResponse("success",cart));
    } catch (ResourceNotFoundException e) {
      return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(),null));
    }
  } 

  @DeleteMapping("/{cartId}/clear")
  public ResponseEntity<ApiResponse> clearCart(@PathVariable Long cartId){
    cartService.clearCart(cartId);
    return ResponseEntity.ok(new ApiResponse("clear cart success!", cartId));
  }

  @GetMapping("/{cartId}/cart/total-price")
  public ResponseEntity<ApiResponse> getTotalAmount(@PathVariable Long cartId){
    try {
      BigDecimal totalAmount = cartService.getTotalPrice(cartId);
    return ResponseEntity.ok(new ApiResponse("total price", totalAmount));
    } catch (Exception e) {
      return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
    }
  }

  
}
