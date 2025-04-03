package com.ecommerce.ecommerce.controller;

import static org.springframework.http.HttpStatus.NOT_FOUND;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.ecommerce.exception.ResourceNotFoundException;
import com.ecommerce.ecommerce.model.Cart;
import com.ecommerce.ecommerce.model.User;
import com.ecommerce.ecommerce.response.ApiResponse;
import com.ecommerce.ecommerce.service.cart.ICartItemService;
import com.ecommerce.ecommerce.service.cart.ICartService;
import com.ecommerce.ecommerce.service.user.IUserService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/cartItems")
public class CartItemController {
  private final ICartItemService cartItemService;
  private final ICartService cartService;
  private final IUserService userService;

  @PostMapping("/item/add")
  public ResponseEntity<ApiResponse> addItemToCart(
                                                   @RequestParam Long productId,
                                                   @RequestParam Integer quantity){
       try {
          User user = userService.getAuthenticatedUser();
         Cart cart = cartService.initializeNewCart(user);
         cartItemService.addItemToCart(cart.getId(),productId,quantity);
         return ResponseEntity.ok(new ApiResponse("success", null));
       } catch (ResourceNotFoundException e) {
         return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), NOT_FOUND));
       }
  }
  @DeleteMapping("/{cartId}/item/{productId}/remove")
  public ResponseEntity<ApiResponse> removeProductFromCart(@PathVariable Long cartId, @PathVariable Long productId){

   try {
         cartItemService.deleteItemFromCart(cartId,productId);
         return ResponseEntity.ok(new ApiResponse("remove success", null));
       } catch (ResourceNotFoundException e) {
         return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), NOT_FOUND));
       }
  }

  @PutMapping("/cart/{cartId}/item/{productId}/update")
  public ResponseEntity<ApiResponse> updateItemQuantity(@PathVariable Long cartId,
                                                        @PathVariable Long productId, 
                                                        @RequestParam int quantity){
     try {
         cartItemService.updateItemQuantity(cartId,productId,quantity);
         return ResponseEntity.ok(new ApiResponse("update success", null));
       } catch (ResourceNotFoundException e) {
         return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), NOT_FOUND));
       }
  }
}
