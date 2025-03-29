package com.ecommerce.ecommerce.service.product;
import java.util.List;

import com.ecommerce.ecommerce.dto.ProductDto;
import com.ecommerce.ecommerce.model.Product;
import com.ecommerce.ecommerce.request.AddProductRequest;
import com.ecommerce.ecommerce.request.ProductUpdateRequest;


public interface IProductService {
  Product addProduct(AddProductRequest product);
  Product getProductById(Long id);
  void deleteProductById(Long id);
  Product updateProductById(ProductUpdateRequest request,Long productId);
  List<Product> getAllProducts();
  List<Product> getProductsByCategory(String category);
  List<Product> getProductsByBrand(String brand);
  List<Product> getProductsByCategoryAndBrand(String category, String brand);
  List<Product> getProductsByName(String name);
  List<Product> getProductsByBrandAndName(String category, String name);
  Long countProductsByBrandAndName(String brand, String name);
  ProductDto convertProductToDto(Product product);
  List<ProductDto> getConvertedProducts(List<Product> products);
}
