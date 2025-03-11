package com.ecommerce.ecommerce.service.cart;

import com.ecommerce.ecommerce.model.Cart;

public interface ICartService {
  Cart getCart(Long id);
  void clearCart();
  
}
