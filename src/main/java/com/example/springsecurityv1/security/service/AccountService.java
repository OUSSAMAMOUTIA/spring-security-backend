package com.example.springsecurityv1.security.service;

import com.example.springsecurityv1.security.entity.AppRole;
import com.example.springsecurityv1.security.entity.AppUser;
import java.util.List;

public interface AccountService {

  AppUser addNewUser(AppUser appUser);

  AppRole addNewRole(AppRole appRole);

  void addRoleToUser(String username, String roleName);

  AppUser findUserByUsername(String username);

  List<AppUser> listUsers();

  int deleteUser(String username);

  int deleteRole(String rolename, String username);
}
