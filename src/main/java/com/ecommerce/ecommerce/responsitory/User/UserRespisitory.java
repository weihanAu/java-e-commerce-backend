package com.ecommerce.ecommerce.responsitory.User;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ecommerce.ecommerce.model.User;

public interface UserRespisitory extends JpaRepository<User,Long> {
  boolean existsByEmail(String Email);
  User findByEmail(String email);
}
