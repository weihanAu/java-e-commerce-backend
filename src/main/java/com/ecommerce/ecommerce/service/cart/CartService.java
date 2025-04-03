package com.ecommerce.ecommerce.service.cart;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.stereotype.Service;

import com.ecommerce.ecommerce.exception.ResourceNotFoundException;
import com.ecommerce.ecommerce.model.Cart;
import com.ecommerce.ecommerce.model.User;
import com.ecommerce.ecommerce.responsitory.Cart.CartItemResponsitory;
import com.ecommerce.ecommerce.responsitory.Cart.CartResponsitory;
import com.ecommerce.ecommerce.service.user.IUserService;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CartService implements ICartService {
  private final CartResponsitory cartResponsitory;
  private final CartItemResponsitory cartItemResponsitory;
  private final IUserService userService;

  @Override
  public Cart getCart(Long id) {
    Cart cart = cartResponsitory.findById(id).orElseThrow(()->new ResourceNotFoundException("not found"));
    BigDecimal totalAmount = cart.getTotalAmount();
    cart.setTotalAmount(totalAmount);
    return cartResponsitory.save(cart);
  }

  @Transactional
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

  @Override
  public Cart getCartByUserId(Long userId) {
      return cartResponsitory.findByUserId(userId);
  }

  @Override
  public Cart initializeNewCart(User user){
   return Optional.ofNullable(getCartByUserId(user.getId()))
    .orElseGet(()->{
                Cart cart = new Cart();
                cart.setUser(user);
                return cartResponsitory.save(cart);
   });
  }
 
}
