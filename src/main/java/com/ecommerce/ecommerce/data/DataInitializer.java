package com.ecommerce.ecommerce.data;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import com.ecommerce.ecommerce.model.User;
import com.ecommerce.ecommerce.responsitory.User.UserRespisitory;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class DataInitializer implements ApplicationListener<ApplicationReadyEvent> {
  private final UserRespisitory userRespisitory;
  
  @Override
  public void onApplicationEvent(ApplicationReadyEvent event) {
   createDefaultUserIfNotExists();
  }

  private void createDefaultUserIfNotExists(){

    for(int i=1;i<=5;i++){
      String defaultEmail = "user"+i+"@gmail.com";
      if(userRespisitory.existsByEmail(defaultEmail))continue;
      User user = new User();
      user.setFirstName("the user");
      user.setLastName("user"+i);
      user.setEmail(defaultEmail);
      user.setPassword("123456");
      userRespisitory.save(user);
    }
  }
}
