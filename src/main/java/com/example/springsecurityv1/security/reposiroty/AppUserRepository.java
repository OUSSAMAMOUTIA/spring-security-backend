package com.example.springsecurityv1.security.reposiroty;

import com.example.springsecurityv1.security.entity.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

@Component
public interface AppUserRepository extends JpaRepository<AppUser, Long> {

  AppUser findByUsername(String username);
}
