package com.ecommerce.ecommerce.service.user;

import com.ecommerce.ecommerce.dto.UserDto;
import com.ecommerce.ecommerce.model.User;
import com.ecommerce.ecommerce.request.CreateUserRequest;
import com.ecommerce.ecommerce.request.UserUpdateRequest;

public interface IUserService  {
    User getUserById(Long userId);
    User createUser(CreateUserRequest request);
    User updateUser(UserUpdateRequest request, Long userId);
    void deleteUser(Long userId);

    UserDto convertUserToDto(User user);

    User getAuthenticatedUser();
}









