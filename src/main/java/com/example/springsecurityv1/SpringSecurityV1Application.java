package com.example.springsecurityv1;

import com.example.springsecurityv1.security.entity.AppRole;
import com.example.springsecurityv1.security.entity.AppUser;
import com.example.springsecurityv1.security.service.AccountServiceImpl;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
//@EnableGlobalMethodSecurity(prePostEnabled = true,securedEnabled = true)
public class SpringSecurityV1Application implements CommandLineRunner {
  @Autowired
  private AccountServiceImpl accountService;
  public static void main(String[] args) {
    SpringApplication.run(SpringSecurityV1Application.class, args);
  }

  @Bean
  PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Override
  public void run(String... args) throws Exception {
    accountService.addNewRole(new AppRole(null,"USER"));
    accountService.addNewRole(new AppRole(null,"ADMIN"));
    accountService.addNewRole(new AppRole(null,"CUSTOMER_MANAGER"));
    accountService.addNewRole(new AppRole(null,"PRODUCT_MANAGER"));
    accountService.addNewRole(new AppRole(null,"BILLS_MANAGER"));
    accountService.addNewUser(new AppUser(null,"admin","1234",new ArrayList<>()));
    accountService.addNewUser(new AppUser(null,"user1","1234",new ArrayList<>()));
    accountService.addNewUser(new AppUser(null,"user2","1234",new ArrayList<>()));
    accountService.addNewUser(new AppUser(null,"user3","1234",new ArrayList<>()));
    accountService.addNewUser(new AppUser(null,"user4","1234",new ArrayList<>()));
    accountService.addRoleToUser("admin","USER");
    accountService.addRoleToUser("admin","ADMIN");
    accountService.addRoleToUser("user1","USER");
    accountService.addRoleToUser("user2","CUSTOMER_MANAGER");
    accountService.addRoleToUser("user2","USER");
    accountService.addRoleToUser("user3","USER");
    accountService.addRoleToUser("user3","PRODUCT_MANAGER");
    accountService.addRoleToUser("user4","USER");
    accountService.addRoleToUser("user4","BILLS_MANAGER");

  }
}
