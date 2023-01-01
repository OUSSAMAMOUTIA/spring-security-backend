package com.example.springsecurityv1.security.service;

import com.example.springsecurityv1.security.entity.AppUser;
import java.util.ArrayList;
import java.util.Collection;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

  private AccountServiceImpl accountService;

  public UserDetailsServiceImpl(AccountServiceImpl accountService) {
    this.accountService = accountService;
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    AppUser appUser = accountService.findUserByUsername(username);
    // I used authorities to convert my roles into granthed
    // authority which exist in spring security
    Collection<GrantedAuthority> authorities = new ArrayList<>();
    appUser.getAppRoles().forEach(appRole -> {
      authorities.add(new SimpleGrantedAuthority(appRole.getRoleName()));
    });
    return new User(appUser.getUsername(), appUser.getPassword(), authorities);
  }
}
