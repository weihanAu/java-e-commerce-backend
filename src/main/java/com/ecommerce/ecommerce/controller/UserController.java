package com.ecommerce.ecommerce.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.ecommerce.dto.UserDto;
import com.ecommerce.ecommerce.exception.ResourceNotFoundException;
import com.ecommerce.ecommerce.model.User;
import com.ecommerce.ecommerce.request.CreateUserRequest;
import com.ecommerce.ecommerce.request.UserUpdateRequest;
import com.ecommerce.ecommerce.response.ApiResponse;
import com.ecommerce.ecommerce.service.user.UserService;

import lombok.RequiredArgsConstructor;
import static org.springframework.http.HttpStatus.*;

import java.nio.channels.AlreadyBoundException;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/users")
public class UserController {

  private final UserService userService;

  @GetMapping("/{userId}/user")
  public ResponseEntity<ApiResponse> getUserById(@PathVariable Long userId){
    try {
      User user = userService.getUserById(userId);
      UserDto userDto = userService.convertUserToDto(user);
      return ResponseEntity.ok(new ApiResponse("user found!", userDto));
    } catch (ResourceNotFoundException e) {
      return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), NOT_FOUND));
    }
  }

  @PostMapping("/add/user")
  public ResponseEntity<ApiResponse> createUser(@RequestBody CreateUserRequest user){

    try {
      User newUser = userService.createUser(user);
      UserDto userDto = userService.convertUserToDto(newUser);
      return ResponseEntity.ok(new ApiResponse("user created", userDto));
    } catch (AlreadyBoundException e) {
      return ResponseEntity.status(CONFLICT).body(new ApiResponse(e.getMessage(), CONFLICT));
    }
  }

  @PutMapping("/{userId}/update")
  public ResponseEntity<ApiResponse> updateUser(@RequestBody UserUpdateRequest request, Long userId){
    try {
      User user = userService.updateUser(request, userId);
      UserDto userDto = userService.convertUserToDto(user);
      return ResponseEntity.ok(new ApiResponse("user updated success", userDto));
    } catch (ResourceNotFoundException e) {
      return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), NOT_FOUND));
    }
  }

  @DeleteMapping("/{userId}/delete")
  public ResponseEntity<ApiResponse> deleteUser(Long userId){

    try {
      userService.deleteUser(userId);
      return ResponseEntity.ok(new ApiResponse("user deleted", userId));
    } catch (ResourceNotFoundException e) {
       return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
    }
  }
  
}
