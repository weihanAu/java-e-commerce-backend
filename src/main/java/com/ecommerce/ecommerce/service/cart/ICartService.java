package com.ecommerce.ecommerce.service.cart;

import java.math.BigDecimal;

import com.ecommerce.ecommerce.model.Cart;

public interface ICartService {
  Cart getCart(Long id);
  void clearCart(Long id);
  BigDecimal getTotalPrice(Long id);
  Long initializeNewCart();
}
