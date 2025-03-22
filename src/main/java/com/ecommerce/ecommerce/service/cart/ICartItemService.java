package com.ecommerce.ecommerce.service.cart;

import com.ecommerce.ecommerce.model.CartItem;

public interface ICartItemService {
  void addItemToCart(Long cartId, Long ProductId, int quantity);
  void deleteItemFromCart(Long cartId, Long ProductId);
  void updateItemQuantity(Long cartId, Long ProductId, int quantity);
  CartItem getCartItem(Long cartId,Long productId);
}
