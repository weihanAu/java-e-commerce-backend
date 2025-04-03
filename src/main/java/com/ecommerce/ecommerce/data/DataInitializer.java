package com.ecommerce.ecommerce.data;

import java.util.Set;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.ecommerce.ecommerce.model.Role;
import com.ecommerce.ecommerce.model.User;
import com.ecommerce.ecommerce.responsitory.User.UserRespisitory;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class DataInitializer implements ApplicationListener<ApplicationReadyEvent> {
  private final UserRespisitory userRespisitory;
  private final PasswordEncoder passwordEncoder;
  private final RoleRepository roleRepository;
  
  @Transactional
  @Override
  public void onApplicationEvent(ApplicationReadyEvent event) {
   Set<String> defaultRoles = Set.of("ROLE_ADMIN","ROLE_USER");
   createDefaultRoleIfNotExits(defaultRoles);
   createDefaultUserIfNotExits();
   createDefaultAdminIfNotExits();
  }

  private void createDefaultUserIfNotExits(){
    Role userRole = roleRepository.findByName("ROLE_USER").get();
    for (int i = 1; i<=5; i++){
        String defaultEmail = "user"+i+"@email.com";
        if (userRespisitory.existsByEmail(defaultEmail)){
            continue;
        }
        User user = new User();
        user.setFirstName("The User");
        user.setLastName("User" + i);
        user.setEmail(defaultEmail);
        user.setPassword(passwordEncoder.encode("123456"));
        user.setRoles(Set.of(userRole));
        userRespisitory.save(user);
        System.out.println("Default vet user " + i + " created successfully.");
    }
    }

  private void createDefaultRoleIfNotExits(Set<String> roles){
      roles.stream()
              .filter(role -> roleRepository.findByName(role).isEmpty())
              .map(Role:: new).forEach(roleRepository::save);

  }

  private void createDefaultAdminIfNotExits(){
    Role adminRole = roleRepository.findByName("ROLE_ADMIN").get();
    for (int i = 1; i<=2; i++){
        String defaultEmail = "admin"+i+"@email.com";
        if (userRespisitory.existsByEmail(defaultEmail)){
            continue;
        }
        User user = new User();
        user.setFirstName("Admin");
        user.setLastName("Admin" + i);
        user.setEmail(defaultEmail);
        user.setPassword(passwordEncoder.encode("123456"));
        user.setRoles(Set.of(adminRole));
        userRespisitory.save(user);
        System.out.println("Default admin user " + i + " created successfully.");
    }
}
}
