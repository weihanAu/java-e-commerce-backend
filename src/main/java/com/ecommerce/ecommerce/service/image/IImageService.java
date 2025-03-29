package com.ecommerce.ecommerce.service.image;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.ecommerce.ecommerce.dto.ImageDto;
import com.ecommerce.ecommerce.model.Image;

public interface IImageService {

  Image getImageById(Long id);
  void deleteImageById(Long id);
  List<ImageDto> saveImage(List<MultipartFile> files, Long productId);
  void updateImage(MultipartFile file, Long imageId);
}
