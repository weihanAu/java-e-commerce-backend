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
import com.ecommerce.ecommerce.response.ApiResponse;
import com.ecommerce.ecommerce.service.cart.CartItemService;
import com.ecommerce.ecommerce.service.cart.CartService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/cartItems")
public class CartItemController {
  private final CartItemService cartItemService;
  private final CartService cartService;

  @PostMapping("/item/add")
  public ResponseEntity<ApiResponse> addItemToCart(@RequestParam(required = false) Long cartId, 
                                                   @RequestParam Long productId,
                                                   @RequestParam Integer quantity){
       try {
         if(cartId==null || cartId < 0){
          cartId = cartService.initializeNewCart();
         }
         cartItemService.addItemToCart(cartId,productId,quantity);
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
