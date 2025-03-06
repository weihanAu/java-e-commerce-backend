package com.ecommerce.ecommerce.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.ecommerce.exception.ResourceNotFoundException;
import com.ecommerce.ecommerce.model.Product;
import com.ecommerce.ecommerce.request.AddProductRequest;
import com.ecommerce.ecommerce.response.ApiResponse;
import com.ecommerce.ecommerce.service.product.IProductService;
import static org.springframework.http.HttpStatus.*;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.prefix}/products")
public class ProductController {
  private final IProductService productService;

  @GetMapping("/all")
  public ResponseEntity<ApiResponse> getAllProducts(){
    try {
      List<Product> products = productService.getAllProducts();
      return ResponseEntity.ok(new ApiResponse("found", products));
    } catch (Exception e) {
      return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(), INTERNAL_SERVER_ERROR));
    }
  }

  @GetMapping("/product/{productId}/product")
  public ResponseEntity<ApiResponse> getProductById(@PathVariable("productId") Long id){
    try {
      Product product = productService.getProductById(id);
      return ResponseEntity.ok(new ApiResponse("success", product));
    } catch (ResourceNotFoundException e) {
      return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(),NOT_FOUND));
    }
  }

  @PostMapping("/add")
  public ResponseEntity<ApiResponse> addProduct(@RequestBody AddProductRequest product){
    try {
      Product theProduct = productService.addProduct(product);
      return ResponseEntity.ok(new ApiResponse("add product success", theProduct));
    } catch (Exception e) {
      return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(), null));
    }
  }
}
