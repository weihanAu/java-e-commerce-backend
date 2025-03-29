package com.ecommerce.ecommerce.CategoryRepository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ecommerce.ecommerce.model.Category;

public interface CategoryRepository extends JpaRepository<Category,Long>{

  Category findByName(String category);
  Boolean existsByName(String name);
}