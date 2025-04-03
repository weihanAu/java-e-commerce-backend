package com.ecommerce.ecommerce.service.cart;

import java.math.BigDecimal;

import com.ecommerce.ecommerce.model.Cart;
import com.ecommerce.ecommerce.model.User;

public interface ICartService {
  Cart getCart(Long id);
  void clearCart(Long id);
  BigDecimal getTotalPrice(Long id);
  Cart initializeNewCart(User user);
  Cart getCartByUserId(Long userId);
}
