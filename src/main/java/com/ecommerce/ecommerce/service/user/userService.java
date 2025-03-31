package com.ecommerce.ecommerce.service.user;

import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.ecommerce.ecommerce.dto.UserDto;
import com.ecommerce.ecommerce.exception.AlreadyExistException;
import com.ecommerce.ecommerce.exception.ResourceNotFoundException;
import com.ecommerce.ecommerce.model.User;
import com.ecommerce.ecommerce.request.CreateUserRequest;
import com.ecommerce.ecommerce.request.UserUpdateRequest;
import com.ecommerce.ecommerce.responsitory.User.UserRespisitory;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService {
  private final UserRespisitory userRespisitory;
  private final ModelMapper modelMapper;

  @Override
  public User getUserById(Long userId) {
    return userRespisitory.findById(userId)
            .orElseThrow(()->new ResourceNotFoundException("user not found!"));
  }

  @Override
  public User createUser(CreateUserRequest request) {
    return Optional.of(request)
            .filter(user->!userRespisitory.existsByEmail(request.getEmail()))
            .map(req -> {
              User newUser = new User();
              newUser.setEmail(req.getEmail());
              newUser.setPassword(req.getPassword());
              newUser.setFirstName(req.getFirstName());
              newUser.setLastName(req.getLastName());
              return userRespisitory.save(newUser);
            }).orElseThrow(()->new AlreadyExistException(request.getEmail() + " already exists!"));
  }

  @Override
  public User updateUser(UserUpdateRequest request, Long userId) {
    return userRespisitory.findById(userId).map(existingUser->{
      existingUser.setFirstName(request.getFirstName());
      existingUser.setLastName(request.getLastName());
      return userRespisitory.save(existingUser);
    })
    .orElseThrow(()->new ResourceNotFoundException("user not found!"));
  }

  @Override
  public void deleteUser(Long userId) {
    userRespisitory.findById(userId)
                   .ifPresentOrElse(userRespisitory::delete, ()->new ResourceNotFoundException("User not found!"));
  }

  @Override
  public UserDto convertUserToDto(User user) {
    return modelMapper.map(user,UserDto.class);
  }

}
