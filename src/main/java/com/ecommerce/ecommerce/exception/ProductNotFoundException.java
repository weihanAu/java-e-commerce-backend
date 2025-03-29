package com.ecommerce.ecommerce.exception;

public class ProductNotFoundException extends RuntimeException{

  public ProductNotFoundException(String message){
    //super call parent calss 's consturctor method when you new a child class.
    super(message);
  }
  
}
