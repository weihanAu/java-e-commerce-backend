package com.ecommerce.ecommerce.service.product;

import com.ecommerce.ecommerce.model.Product;

import java.util.List;

import com.ecommerce.ecommerce.ProductRepository.ProductRepository;
import com.ecommerce.ecommerce.exception.ProductNotFoundException;

public class ProductService implements IProductService {
  private ProductRepository productRepository;
  
  @Override
  public List<Product> getAllProducts(){
    return productRepository.findAll();
  }

  @Override
  public List<Product> getProductsByCategoryAndBrand(String category, String brand){
    return productRepository.findByCategoryNameAndBrand(category,brand);
  }
  
  @Override
  public Product getProductById(Long id){
    return productRepository.findById(id)
            .orElseThrow(()-> new ProductNotFoundException("Product not found"));
  }

  @Override
  public List<Product> getProductsByBrand(String brand){
    return productRepository.findByBrand(brand);
  }

  @Override
  public void deleteProductById(Long id){
    productRepository.findById(id)
      .ifPresentOrElse(productRepository::delete,()->{throw new ProductNotFoundException("Product not found");});
  }

  @Override
  public List<Product> getProductsByCategory(String category){
    return productRepository.findByCategoryName(category);
  }
}
