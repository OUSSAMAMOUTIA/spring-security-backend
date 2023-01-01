package com.example.springsecurityv1.security.service;

import com.example.springsecurityv1.security.entity.AppRole;
import com.example.springsecurityv1.security.entity.AppUser;
import com.example.springsecurityv1.security.reposiroty.AppRoleRepository;
import com.example.springsecurityv1.security.reposiroty.AppUserRepository;
import java.util.List;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class AccountServiceImpl implements AccountService {

  private AppUserRepository appUserRepository;
  private AppRoleRepository appRoleRepository;
  private PasswordEncoder passwordEncoder;

  public AccountServiceImpl(AppUserRepository appUserRepository,
      AppRoleRepository appRoleRepository,
      PasswordEncoder passwordEncoder) {
    this.appUserRepository = appUserRepository;
    this.appRoleRepository = appRoleRepository;
    this.passwordEncoder = passwordEncoder;
  }

  @Override
  public AppUser addNewUser(AppUser appUser) {
    String password = appUser.getPassword();
    appUser.setPassword(passwordEncoder.encode(password));
    return appUserRepository.save(appUser);
  }

  @Override
  public AppRole addNewRole(AppRole appRole) {
    return appRoleRepository.save(appRole);
  }

  @Override
  public void addRoleToUser(String username, String roleName) {
    AppUser appUser = appUserRepository.findByUsername(username);
    AppRole appRole = appRoleRepository.findByRoleName(roleName);
//    if(appUser==null){
//      appUser=new AppUser();
//    }
    appUser.getAppRoles().add(appRole);
  }

  @Override
  public AppUser findUserByUsername(String username) {
    return appUserRepository.findByUsername(username);
  }

  public int deleteUser(String username) {
    AppUser user = appUserRepository.findByUsername(username);
    if (user != null) {
      appUserRepository.delete(user);
      return 1;
    } else {
      return -1;
    }
  }

  public int deleteRole(String roleName, String username) {
    AppRole role = appRoleRepository.findByRoleName(roleName);
    AppUser user = appUserRepository.findByUsername(username);
    if (role != null) {
      user.getAppRoles().remove(role);
      appRoleRepository.delete(role);
      return 1;
    } else {
      return -1;
    }
  }

  @Override
  public List<AppUser> listUsers() {
    return appUserRepository.findAll();
  }
}
