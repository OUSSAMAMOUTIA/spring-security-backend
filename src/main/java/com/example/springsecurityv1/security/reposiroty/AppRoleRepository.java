package com.example.springsecurityv1.security.reposiroty;

import com.example.springsecurityv1.security.entity.AppRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

@Component
public interface AppRoleRepository extends JpaRepository<AppRole, Long> {

  AppRole findByRoleName(String roleName);
}
