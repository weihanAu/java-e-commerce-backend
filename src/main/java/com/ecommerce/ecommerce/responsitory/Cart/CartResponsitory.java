package com.ecommerce.ecommerce.responsitory.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

import com.ecommerce.ecommerce.model.Cart;

public interface CartResponsitory extends JpaRepository<Cart,Long>{
   Cart findByUserId(Long userId);
}
