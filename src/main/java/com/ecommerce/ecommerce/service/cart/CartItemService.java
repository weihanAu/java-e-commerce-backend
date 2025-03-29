package com.ecommerce.ecommerce.service.cart;

import java.math.BigDecimal;
import java.util.HashSet;

import org.springframework.stereotype.Service;

import com.ecommerce.ecommerce.exception.ResourceNotFoundException;
import com.ecommerce.ecommerce.model.Cart;
import com.ecommerce.ecommerce.model.CartItem;
import com.ecommerce.ecommerce.model.Product;
import com.ecommerce.ecommerce.responsitory.Cart.CartItemResponsitory;
import com.ecommerce.ecommerce.responsitory.Cart.CartResponsitory;
import com.ecommerce.ecommerce.service.product.ProductService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CartItemService implements ICartItemService {

  private final CartItemResponsitory cartItemResponsitory;
  private final CartResponsitory cartResponsitory;
  private final CartService cartService;
  private final ProductService productService;
  
  @Override
  public void addItemToCart(Long cartId, Long ProductId, int quantity) {
    //1. get the cart
    //2. get the product
    //3. check if the item already exist in the cart?
    //4. if yes, then increate the quantity
    //5. if no,  then then initiate a new cartItem entry.
    Cart cart = cartService.getCart(cartId);
    Product product = productService.getProductById(ProductId);

     // Ensure cart's items list is never null (initialize if null)
      if (cart.getItems() == null) {
          cart.setItems(new HashSet<>());
      }
    CartItem cartItem = cart.getItems()
                .stream()
                .filter(item->item.getProduct().getId().equals(ProductId))
                .findFirst()
                .orElse(new CartItem());
    if(cartItem.getId()==null){
      cartItem.setProduct(product);
      cartItem.setCart(cart);
      cartItem.setQuantity(quantity);
      cartItem.setUnitPrice(product.getPrice());
    }
    else{
      cartItem.setQuantity(cartItem.getQuantity()+ quantity);
    }
    cartItem.setTotalPrice();
    cart.addItem(cartItem);
    cartItemResponsitory.save(cartItem);
    cartResponsitory.save(cart);
  }

  @Override
  public void deleteItemFromCart(Long cartId, Long productId) {
    Cart cart = cartService.getCart(cartId);
    CartItem itemToRemove = getCartItem(cartId,productId);
    cart.removeItem(itemToRemove);
    cartResponsitory.save(cart);
  }

  @Override
  public void updateItemQuantity(Long cartId, Long ProductId, int quantity) {
   Cart cart = cartService.getCart(cartId);
    cart.getItems()
        .stream()
        .filter(item -> item.getId().equals(ProductId))
        .findFirst()
        .ifPresent(item ->{
          item.setQuantity(quantity);
          item.setUnitPrice(item.getProduct().getPrice());
          item.setTotalPrice();
        });
        
        BigDecimal totalAmount = cart.getItems()
                                    .stream()
                                    .map(CartItem::getTotalPrice)
                                    .reduce(BigDecimal.ZERO,BigDecimal::add);
        cart.setTotalAmount(totalAmount);
        cartResponsitory.save(cart);              
  }

  @Override
    public CartItem getCartItem(Long cartId, Long productId) {
        Cart cart = cartService.getCart(cartId);
        return  cart.getItems()
                .stream()
                .filter(item -> item.getProduct().getId().equals(productId))
                .findFirst().orElseThrow(() -> new ResourceNotFoundException("Item not found"));
    }

}
