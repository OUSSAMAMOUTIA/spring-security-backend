package com.example.springsecurityv1.security.ws;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.springsecurityv1.security.entity.AppRole;
import com.example.springsecurityv1.security.entity.AppUser;
import com.example.springsecurityv1.security.service.AccountServiceImpl;
import com.example.springsecurityv1.security.utility.JWTUtil;
import com.example.springsecurityv1.security.utility.UserRole;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AccountWs {

  private AccountServiceImpl accountServiceImpl;

  public AccountWs(AccountServiceImpl accountService) {
    this.accountServiceImpl = accountService;
  }

  @GetMapping(path = "/users")
//  @PostAuthorize("hasAuthority('USER')")
  private List<AppUser> getAllUSERS() {
    return accountServiceImpl.listUsers();
  }

  @GetMapping(path = "/user/profile/{username}")
//  @PostAuthorize("hasAuthority('USER')")
  private AppUser Profile(@PathVariable("username") String username) {
    return accountServiceImpl.findUserByUsername(username);
  }

  @PostMapping("/add-user")
//  @PostAuthorize("hasAuthority('ADMIN')")
  private AppUser addNewUser(@RequestBody AppUser appUser) {
    return accountServiceImpl.addNewUser(appUser);
  }

  @PostMapping("/add-role")
//  @PostAuthorize("hasAuthority('ADMIN')")
  private AppRole addNewRole(@RequestBody AppRole appRole) {
    return accountServiceImpl.addNewRole(appRole);
  }

  @PostMapping("/add-role-to-user")
//  @PostAuthorize("hasAuthority('ADMIN')")
  private void AddRoleToUser(@RequestBody UserRole userRole) {
    accountServiceImpl.addRoleToUser(userRole.getUsername(), userRole.getRoleName());
  }

  @DeleteMapping("/user/{username}")
//  @PostAuthorize("hasAuthority('ADMIN')")
  private int deleteUser(@PathVariable String username) {
    return accountServiceImpl.deleteUser(username);
  }

  @DeleteMapping("/role/{rolename}/{username}")
//  @PostAuthorize("hasAuthority('ADMIN')")
  private int deleteRole(@PathVariable("rolename") String rolename,
      @PathVariable("username") String username) {
    return accountServiceImpl.deleteRole(rolename, username);
  }

  @GetMapping("/refresh-token")
  private void refreshToken(HttpServletRequest request, HttpServletResponse response)
      throws Exception {
    String authorizationToken = request.getHeader(JWTUtil.AUT_HEADER);
    if (authorizationToken != null && authorizationToken.startsWith(JWTUtil.PREFIX)) {
      try {
        String jwt = authorizationToken.substring(JWTUtil.PREFIX.length());
        Algorithm algorithm = Algorithm.HMAC256(JWTUtil.SECRET);
        JWTVerifier jwtVerifier = JWT.require(algorithm).build();
        DecodedJWT decodedJWT = jwtVerifier.verify(jwt);
        String username = decodedJWT.getSubject();
        AppUser appUser = accountServiceImpl.findUserByUsername(username);
        String jwtAccessToken = JWT.create().
            withSubject(appUser.getUsername()).
            withExpiresAt(new Date(System.currentTimeMillis() + JWTUtil.EXPIRE_ACCESS_TOKEN)).
            withIssuer(request.getRequestURL().toString()).
            withClaim("roles",
                appUser.getAppRoles().stream().map(role -> role.getRoleName()).collect(
                    Collectors.toList())).
            sign(algorithm);
        Map<String, String> idToken = new HashMap<>();
        idToken.put("access-token", jwtAccessToken);
        idToken.put("refresh-token", jwt);
        response.setContentType("application/json");
        new ObjectMapper().writeValue(response.getOutputStream(), idToken);
      } catch (Exception e) {
        throw e;
      }
    } else {
      throw new RuntimeException("Refresh Token Required!!!!!");
    }
  }
}
