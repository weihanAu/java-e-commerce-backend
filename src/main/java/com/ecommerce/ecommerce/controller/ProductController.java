package com.ecommerce.ecommerce.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.ecommerce.dto.ProductDto;
import com.ecommerce.ecommerce.exception.ResourceNotFoundException;
import com.ecommerce.ecommerce.model.Product;
import com.ecommerce.ecommerce.request.AddProductRequest;
import com.ecommerce.ecommerce.request.ProductUpdateRequest;
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
      List<ProductDto> productDtos = productService.getConvertedProducts(products);
      return ResponseEntity.ok(new ApiResponse("found", productDtos));
    } catch (Exception e) {
      return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(), INTERNAL_SERVER_ERROR));
    }
  }

  @GetMapping("/product/{productId}/product")
  public ResponseEntity<ApiResponse> getProductById(@PathVariable("productId") Long id){
    try {
      Product product = productService.getProductById(id);
      ProductDto productDto = productService.convertProductToDto(product);
      return ResponseEntity.ok(new ApiResponse("success", productDto));
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

  @PutMapping("/product/{productId}/update")
  public ResponseEntity<ApiResponse> updateProduct (@RequestBody ProductUpdateRequest product, @PathVariable Long productId){
    try {
      Product existingProduct = productService.updateProductById(product, productId);
      return ResponseEntity.ok(new ApiResponse("success", existingProduct));
    } catch (ResourceNotFoundException e) {
      return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), NOT_FOUND));
    }
  }

  @DeleteMapping("/product/{id}/delete")
  public ResponseEntity<ApiResponse> deleteProduct(@PathVariable Long id){
    try {
      productService.deleteProductById(id);
      return ResponseEntity.ok(new ApiResponse("delete success", id));
    } catch (ResourceNotFoundException e) {
      return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), NOT_FOUND));
    }
  }

  @GetMapping("/products/by/name-and-brand")
  public ResponseEntity<ApiResponse> getProductsByBrandandName(@PathVariable String brand,@PathVariable String name){
     try {
      List<Product> products = productService.getProductsByBrandAndName(brand, name);
      List<ProductDto> productDtos = productService.getConvertedProducts(products);
      if(products.isEmpty()){
        return ResponseEntity.status(NOT_FOUND).body(new ApiResponse("products not found", null));
      }
      return ResponseEntity.ok(new ApiResponse("products found", productDtos));
     } catch (Exception e) {
       return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(), INTERNAL_SERVER_ERROR));
     }
  }

  @GetMapping("/products/by/Category-and-brand")
  public ResponseEntity<ApiResponse> getProductsByBrandandCategory(@PathVariable String category,@PathVariable String brand){
     try {
      List<Product> products = productService.getProductsByCategoryAndBrand(category, brand);
       List<ProductDto> productDtos = productService.getConvertedProducts(products);
      if(products.isEmpty()){
        return ResponseEntity.status(NOT_FOUND).body(new ApiResponse("products not found", null));
      }
      return ResponseEntity.ok(new ApiResponse("products found", productDtos));
     } catch (Exception e) {
       return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(), INTERNAL_SERVER_ERROR));
     }
  }
  
  @GetMapping("/products/{name}/products")
  public ResponseEntity<ApiResponse> getProductsByName(@PathVariable String name){
     try {
      List<Product> products = productService.getProductsByName(name);
      List<ProductDto> productDtos = productService.getConvertedProducts(products);
      if(products.isEmpty()){
        return ResponseEntity.status(NOT_FOUND).body(new ApiResponse("products not found", null));
      }
      return ResponseEntity.ok(new ApiResponse("products found", productDtos));
     } catch (Exception e) {
       return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(), INTERNAL_SERVER_ERROR));
     }
  }

  @GetMapping("/products/by-brand")
  public ResponseEntity<ApiResponse> getProductsByBrand(@PathVariable String brand){
     try {
      List<Product> products = productService.getProductsByBrand(brand);
      List<ProductDto> productDtos = productService.getConvertedProducts(products);
      if(products.isEmpty()){
        return ResponseEntity.status(NOT_FOUND).body(new ApiResponse("products not found", null));
      }
      return ResponseEntity.ok(new ApiResponse("products found", productDtos));
     } catch (Exception e) {
       return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(), INTERNAL_SERVER_ERROR));
     }
  }

  @GetMapping("/products/{category}/products")
  public ResponseEntity<ApiResponse> getProductsByCatogiry(@PathVariable String category){
    try {
      List<Product> products = productService.getProductsByCategory(category);
      List<ProductDto> productDtos = productService.getConvertedProducts(products);
      if(products.isEmpty()){
        return ResponseEntity.status(NOT_FOUND).body(new ApiResponse("products not found", null));
      }
      return ResponseEntity.ok(new ApiResponse("products found", productDtos));
     } catch (Exception e) {
       return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(), INTERNAL_SERVER_ERROR));
     }
  }

  @GetMapping("/products/count/by-brand-name")
  public ResponseEntity<ApiResponse> countProductsByBrandAndName(@PathVariable String brand,@PathVariable String name){
    try {
      Long count = productService.countProductsByBrandAndName(brand, name);
      return ResponseEntity.ok(new ApiResponse("count by name and brand", count));
    } catch (Exception e) {
      return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(), INTERNAL_SERVER_ERROR));
    }
  }
}
