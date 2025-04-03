package com.ecommerce.ecommerce.data;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ecommerce.ecommerce.model.Role;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(String role);
}