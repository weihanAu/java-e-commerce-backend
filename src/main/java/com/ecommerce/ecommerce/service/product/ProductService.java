package com.ecommerce.ecommerce.service.product;

import com.ecommerce.ecommerce.model.Product;
import com.ecommerce.ecommerce.request.AddProductRequest;
import com.ecommerce.ecommerce.request.ProductUpdateRequest;
import com.ecommerce.ecommerce.responsitory.ImageResponsitory;
import com.ecommerce.ecommerce.service.image.ImageService;

import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.ecommerce.ecommerce.model.Category;
import com.ecommerce.ecommerce.model.Image;
import com.ecommerce.ecommerce.CategoryRepository.CategoryRepository;
import com.ecommerce.ecommerce.ProductRepository.ProductRepository;
import com.ecommerce.ecommerce.dto.ImageDto;
import com.ecommerce.ecommerce.dto.ProductDto;
import com.ecommerce.ecommerce.exception.AlreadyExistException;
import com.ecommerce.ecommerce.exception.ProductNotFoundException;

@Service
@RequiredArgsConstructor
public class ProductService implements IProductService {
  private final ProductRepository productRepository;
  private final CategoryRepository categoryRepository;
  private final ModelMapper modelMapper;
  private final ImageResponsitory imageResponsitory;
  
  @Override
  public List<Product>getProductsByBrandAndName(String category, String name){
    return productRepository.findByBrandAndName(category,name);
  }

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

  @Override
  public Product addProduct(AddProductRequest request) {
    //check if category is found
    //if yes, set it as a new product
    //if no, then save it as a new category

    if(productExist(request.getName(),request.getBrand())){
      throw new AlreadyExistException( request.getBrand()+request.getName()+"already exists,you can update the quantity");
    }
    Category category = Optional.ofNullable(categoryRepository.findByName(request.getCategory().getName()))
            .orElseGet(()->{
              Category newCategory = new Category(request.getCategory().getName());
              return categoryRepository.save(newCategory);
            });
    request.setCategory(category);
    return productRepository.save(CreateProduct(request,category));
  }

  private Product CreateProduct(AddProductRequest request, Category category){

    return new Product(
      request.getName(),
      request.getBrand(),
      request.getPrice(),
      request.getInventory(),
      request.getDescription(),
      category
      );
  }

  private Product updateExistingProduct(Product existingProduct, ProductUpdateRequest request){
    existingProduct.setName(request.getName());
    existingProduct.setBrand(request.getBrand());
    existingProduct.setPrice(request.getPrice());
    existingProduct.setInventory(request.getInventory());
    existingProduct.setDescription(request.getDescription());

    Category category = categoryRepository.findByName(request.getCategory().getName());

    existingProduct.setCategory(category);
    return existingProduct;
   
  }

  @Override
  public Product updateProductById(ProductUpdateRequest request, Long productId) {
    
    return productRepository.findById(productId)
           .map(existingProduct->updateExistingProduct(existingProduct, request))
           .map(productRepository::save)
           .orElseThrow(()->new ProductNotFoundException("product not found"));
  }
  
  @Override
  public List<Product> getProductsByName(String name) {
   return productRepository.findByName(name);
  }

  @Override
  public Long countProductsByBrandAndName(String brand, String name) {
    return countProductsByBrandAndName(brand,name);
  }

 @Override
  public List<ProductDto> getConvertedProducts(List<Product> products){
   return  products.stream()
                   .map(this::convertProductToDto)
                   .toList();
  }

  @Override
  public ProductDto convertProductToDto(Product product){
    ProductDto productDto = modelMapper.map(product,ProductDto.class);
    List<Image> images = imageResponsitory.findByProductId(product.getId());
    List<ImageDto> imageDtos = images.stream()
        .map(image -> { return modelMapper.map(image,ImageDto.class);})
        .toList();
    productDto.setImages(imageDtos);
    return productDto;
  }

  private boolean productExist(String name, String brand){
    return productRepository.existsByNameAndBrand(name,brand);
  }
}
