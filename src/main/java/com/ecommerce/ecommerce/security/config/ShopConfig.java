package com.ecommerce.ecommerce.security.config;
import java.util.List;

import org.modelmapper.ModelMapper;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.ecommerce.ecommerce.security.jwt.AuthTokenFilter;
import com.ecommerce.ecommerce.security.jwt.JwtAuthEntryPoint;
import com.ecommerce.ecommerce.security.user.ShopUserDetailService;

import lombok.RequiredArgsConstructor;

@EnableWebSecurity
@RequiredArgsConstructor
@Configuration
public class ShopConfig {

  private final ShopUserDetailService userDetailService;
  private final JwtAuthEntryPoint authEntryPoint;

  private static final List<String> SECURED_URLS =
            List.of("/api/v1/carts/**", "/api/v1/cartItems/**");


  @Bean
  public ModelMapper modelMapper(){
    return new ModelMapper();
  }

  @Bean
  public PasswordEncoder passwordEncoder(){
    return new BCryptPasswordEncoder();
  } 

  @Bean
  public AuthTokenFilter authTokenFilter(){
    return new AuthTokenFilter();
  }

  @Bean
  public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
      return  authConfig.getAuthenticationManager();

  }

  @Bean
  public DaoAuthenticationProvider daoAuthenticationProvider() {
      var authProvider = new DaoAuthenticationProvider();
      authProvider.setUserDetailsService(userDetailService);
      authProvider.setPasswordEncoder(passwordEncoder());
      return authProvider;
  }

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

      http.csrf(AbstractHttpConfigurer::disable)
              .exceptionHandling(exception -> exception.authenticationEntryPoint(authEntryPoint))
              .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
              .authorizeHttpRequests(auth ->auth.requestMatchers(SECURED_URLS.toArray(String[]::new)).authenticated()
                      .anyRequest().permitAll());
      http.authenticationProvider(daoAuthenticationProvider());
      http.addFilterBefore(authTokenFilter(), UsernamePasswordAuthenticationFilter.class);
      return http.build();
      
  }
}



