package com.ecommerce.ecommerce.controller;


import java.util.List;

import org.springframework.core.io.Resource;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.ecommerce.ecommerce.dto.ImageDto;
import com.ecommerce.ecommerce.exception.ResourceNotFoundException;
import com.ecommerce.ecommerce.model.Image;
import com.ecommerce.ecommerce.response.ApiResponse;
import com.ecommerce.ecommerce.service.image.IImageService;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.prefix}/images")
public class ImageController {
  private final IImageService imageService;

  @PostMapping("/upload")
  public ResponseEntity<ApiResponse> saveImages(
    @RequestParam List<MultipartFile> files,
    @RequestParam Long productId){
      try {
        List<ImageDto> imageDtos = imageService.saveImage(files, productId);
      return ResponseEntity.ok(new ApiResponse("upload success!", imageDtos));
      } catch (Exception e) {
        return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse("upload fails!", e.getMessage()));
      }
  }
  
  @GetMapping("/image/download/{imageId}")
  public ResponseEntity<Resource> downloadImage(
    @PathVariable Long imageId
    ){
      try {
        Image image = imageService.getImageById(imageId);
        ByteArrayResource resource = new ByteArrayResource(image.getImage().getBytes(1, (int) image.getImage().length()));
        return ResponseEntity.ok().contentType(MediaType.parseMediaType(image.getFileType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" +image.getFileName() + "\"")
                .body(resource);
        
      } catch (Exception e) {
        
      }
      return null;
    }

  @PutMapping("/image/{imageID}/update")
  public ResponseEntity<ApiResponse> updateImage(@PathVariable Long imageId,@RequestBody MultipartFile newImage){
      try {
        Image currentImage = imageService.getImageById(imageId);
        if(currentImage != null){
          imageService.updateImage(newImage, imageId);
          return ResponseEntity.ok( new ApiResponse("image updated", null));
        }
      } catch (ResourceNotFoundException e) {
        return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
      }

      return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse("update failed", INTERNAL_SERVER_ERROR));
  }

  @DeleteMapping("/image/{imageId}/delete")
  public ResponseEntity<ApiResponse> deleteImage(@PathVariable Long imageId){
   try {
     Image image = imageService.getImageById(imageId);
      if(image != null){
        imageService.deleteImageById(imageId);
        return ResponseEntity.ok(new ApiResponse("delte success", null));
      }
   } catch (ResourceNotFoundException e) {
      return ResponseEntity.status(NOT_FOUND).body(new ApiResponse("image not found", null));
   }
   return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse("delete failed", INTERNAL_SERVER_ERROR));
  }
}
