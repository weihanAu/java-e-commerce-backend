package com.ecommerce.ecommerce.dto;

import lombok.Data;

@Data
public class ImageDto {
  private Long id;
  private String fileName;
  private String downloadUrl;
}
