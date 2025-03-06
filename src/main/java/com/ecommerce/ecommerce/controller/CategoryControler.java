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

import com.ecommerce.ecommerce.response.ApiResponse;
import com.ecommerce.ecommerce.service.category.ICategoryService;
import com.ecommerce.ecommerce.exception.AlreadyExistException;
import com.ecommerce.ecommerce.exception.ResourceNotFoundException;
import com.ecommerce.ecommerce.model.Category;

import lombok.RequiredArgsConstructor;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.CONFLICT;;

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.prefix}/categories")
public class CategoryControler {
  private final ICategoryService categoryService;

  @GetMapping("/all")
  public ResponseEntity<ApiResponse> getAllCateories(){
    try {
      List<Category> categories = categoryService.getAllCategories();
      return ResponseEntity.ok(new ApiResponse("found", categories));
    } catch (Exception e) {
      return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse("error", INTERNAL_SERVER_ERROR));
    }
  }

  @PostMapping("/add/category")
  public ResponseEntity<ApiResponse> addCategory(@RequestBody Category name){
    try {
      Category category = categoryService.addCategory(name);
      return ResponseEntity.ok(new ApiResponse("category created", category));
    } catch (AlreadyExistException e) {
     return ResponseEntity.status(CONFLICT).body(new ApiResponse("already exists", CONFLICT));
    }
  }
  
  @GetMapping("/category/{id}/category")
  public ResponseEntity<ApiResponse> getCategoryById(@PathVariable Long id){
    try {
      Category category = categoryService.getCategoryById(id);
      return ResponseEntity.ok(new ApiResponse("Found", category));
    } catch (ResourceNotFoundException e) {
     return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), NOT_FOUND));
    }
  }

  @GetMapping("/category/{name}/category")
  public ResponseEntity<ApiResponse> getCategoryById(@PathVariable String name){
    try {
      Category category = categoryService.getCategoryByName(name);
      return ResponseEntity.ok(new ApiResponse("Found", category));
    } catch (ResourceNotFoundException e) {
     return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), NOT_FOUND));
    }
  }

  @DeleteMapping("/category/{id}/delete")
  public ResponseEntity<ApiResponse> deleteCategory(@PathVariable Long id){
    try {
      categoryService.deleteCategoryById(id);
      return ResponseEntity.ok(new ApiResponse("delete success!", id));
    } catch (ResourceNotFoundException e) {
      return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), NOT_FOUND));
    }
  }
  
  @PutMapping("/category/{id}/update")
  public ResponseEntity<ApiResponse> updateCategory(@PathVariable Long id, @RequestBody Category category) {
      try {
          Category updatedCategory = categoryService.updateCategory(category, id);
          return ResponseEntity.ok(new ApiResponse("Update success!", updatedCategory));
      } catch (ResourceNotFoundException e) {
          return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
      }
  }
}
