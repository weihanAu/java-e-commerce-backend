package com.ecommerce.ecommerce.service.product;
import java.util.List;

import com.ecommerce.ecommerce.model.Product;

public interface IProductService {
  Product addProdict(Product product);
  Product getProductById(Long id);
  void deleteProductById(Long id);
  void updateProductById(Product product,Long productId);
  List<Product> getAllProducts();
  List<Product> getProductsByCategory(String category);
  List<Product> getProductsByBrand(String brand);
  List<Product> getProductsByCategoryAndBrand(String category, String brand);
  List<Product> getProductsByName(String name);
  List<Product> getProductsByBrandAndName(String category, String name);
  Long countProductsByBrandAndName(String brand, String name);
}
