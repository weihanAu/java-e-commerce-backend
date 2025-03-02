package com.ecommerce.ecommerce.responsitory;
import org.springframework.data.jpa.repository.JpaRepository;

import com.ecommerce.ecommerce.model.Image;

public interface  ImageResponsitory extends JpaRepository<Image,Long>{
  
}
