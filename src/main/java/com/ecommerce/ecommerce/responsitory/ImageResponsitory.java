package com.ecommerce.ecommerce.responsitory;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ecommerce.ecommerce.model.Image;

public interface  ImageResponsitory extends JpaRepository<Image,Long>{
   List<Image> findByProductId(Long id);
}
