package com.ecommerce.ecommerce.security.user;

import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.ecommerce.ecommerce.model.User;
import com.ecommerce.ecommerce.responsitory.User.UserRespisitory;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ShopUserDetailService implements UserDetailsService {
  private final UserRespisitory userRespisitory;

  @Override
  public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
      User user = Optional.ofNullable(userRespisitory.findByEmail(email))
                .orElseThrow(()->new UsernameNotFoundException("user not found"));
      return ShopUserDetails.buildUserDetails(user);
  }
  
}
