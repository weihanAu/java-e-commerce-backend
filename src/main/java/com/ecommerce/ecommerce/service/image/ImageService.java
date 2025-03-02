package com.ecommerce.ecommerce.service.image;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.rowset.serial.SerialBlob;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.ecommerce.ecommerce.dto.ImageDto;
import com.ecommerce.ecommerce.exception.ResourceNotFoundException;
import com.ecommerce.ecommerce.model.Image;
import com.ecommerce.ecommerce.model.Product;
import com.ecommerce.ecommerce.responsitory.ImageResponsitory;
import com.ecommerce.ecommerce.service.product.ProductService;

import lombok.RequiredArgsConstructor;
@Service
@RequiredArgsConstructor
public class ImageService implements IImageService{

  private final ImageResponsitory imageResponsitory;
  private final ProductService productService;

  @Override
  public Image getImageById(Long id) {
    return imageResponsitory.findById(id).orElseThrow(()-> new ResourceNotFoundException("no image found with "+ id));
  }

  @Override
  public void deleteImageById(Long id) {
   imageResponsitory.findById(id).ifPresentOrElse(imageResponsitory::delete, ()->{
    throw new ResourceNotFoundException("no image found with "+ id);
   });
  }

  @Override
  public List<ImageDto> saveImage(List<MultipartFile> files, Long productId) {
    Product product = productService.getProductById(productId);
    List<ImageDto> savedImageDto = new ArrayList<>();
    for (MultipartFile file : files) {
      try {
        Image image = new Image();
        image.setFileName(file.getOriginalFilename());
        image.setFileType(file.getContentType());
        image.setImage(new SerialBlob(file.getBytes()));
        image.setProduct(product);
        
        String buildDownloadUrl = "/api/v1/images/image/download/";
        String downloadUrl = buildDownloadUrl + image.getId();
        image.setDownloadUrl(downloadUrl);
        Image savedImage = imageResponsitory.save(image);

        savedImage.setDownloadUrl(downloadUrl + savedImage.getId());
        imageResponsitory.save(savedImage);

        ImageDto imageDto = new ImageDto();
        imageDto.setId(savedImage.getId());
        imageDto.setFileName(savedImage.getFileName());
        imageDto.setDownloadUrl(savedImage.getDownloadUrl());
        savedImageDto.add(imageDto);

      } catch (IOException | SQLException e) {
        throw new RuntimeException(e.getMessage());
      }
    }
    
   return savedImageDto;
  }

  @Override
  public void updateImage(MultipartFile file, Long imageId) {
    Image image = getImageById(imageId);
    try {
      image.setFileName(file.getOriginalFilename());
      image.setFileType(file.getContentType());
      image.setImage(new SerialBlob(file.getBytes()));
      imageResponsitory.save(image);
    } catch (IOException |SQLException e) {
      throw new RuntimeException(e.getMessage());
    }
  }

}
