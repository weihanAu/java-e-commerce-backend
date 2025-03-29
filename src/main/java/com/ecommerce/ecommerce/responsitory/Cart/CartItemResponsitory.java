package com.ecommerce.ecommerce.responsitory.Cart;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ecommerce.ecommerce.model.CartItem;

public interface CartItemResponsitory extends JpaRepository<CartItem,Long> {
  void deleteAllByCartId(Long id);
}
