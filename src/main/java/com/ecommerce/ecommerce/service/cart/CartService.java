package com.ecommerce.ecommerce.service.cart;

import java.math.BigDecimal;

import org.springframework.stereotype.Service;

import com.ecommerce.ecommerce.exception.ResourceNotFoundException;
import com.ecommerce.ecommerce.model.Cart;
import com.ecommerce.ecommerce.responsitory.Cart.CartItemResponsitory;
import com.ecommerce.ecommerce.responsitory.Cart.CartResponsitory;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CartService implements ICartService {
  private final CartResponsitory cartResponsitory;
  private final CartItemResponsitory cartItemResponsitory;

  @Override
  public Cart getCart(Long id) {
    Cart cart = cartResponsitory.findById(id).orElseThrow(()->new ResourceNotFoundException("not found"));
    BigDecimal totalAmount = cart.getTotalAmount();
    cart.setTotalAmount(totalAmount);
    return cartResponsitory.save(cart);
  }

  @Override
  public void clearCart(Long id) {
    Cart cart = getCart(id);
    cartItemResponsitory.deleteAllByCartId(id);
    cart.getItems().clear();
    cartResponsitory.deleteById(id);
  }

  @Override
  public BigDecimal getTotalPrice(Long id) {
    Cart cart = getCart(id);
    return cart.getTotalAmount();
  }

 
}
