package com.ecommerce.ecommerce.service.category;

import java.util.List;
import java.util.Optional;

import com.ecommerce.ecommerce.CategoryRepository.CategoryRepository;
import com.ecommerce.ecommerce.exception.AlreadyExistException;
import com.ecommerce.ecommerce.exception.ResourceNotFoundException;
import com.ecommerce.ecommerce.model.Category;

public class CategoryService implements ICategoryService {
  
  private CategoryRepository categoryRepository;

  @Override
  public Category getCategoryByName(String name) {
      return categoryRepository.findByName(name);
  }

  @Override
  public List<Category> getAllCategories() {
      return categoryRepository.findAll();
  }

  @Override
  public Category addCategory(Category category) {
    return Optional.of(category).filter(c -> !categoryRepository.existsByName(c.getName()))
    .map(categoryRepository::save)
    .orElseThrow(()->new AlreadyExistException("sss"));
  }

  @Override
  public Category updateCategory(Category category, Long id) {
   return Optional.ofNullable(getCategoryById(id))
    .map(existingCategory->{
      existingCategory.setName(category.getName());
      return categoryRepository.save(existingCategory);
    })
    .orElseThrow(() -> new ResourceNotFoundException("Category not found"));
  }

  @Override
  public void deleteCategoryById(Long id) {
    categoryRepository.findById(id)
      .ifPresentOrElse(categoryRepository::delete, ()->new ResourceNotFoundException("category not found"));  }

  @Override
  public Category getCategoryById(Long id) {
    return categoryRepository.findById(id)
              .orElseThrow(() -> new ResourceNotFoundException("resource not found"));
  }
}
