package com.example.springsecurityv1.security.utility;

import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.springsecurityv1.security.entity.AppUser;
import com.example.springsecurityv1.security.service.AccountServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Bean;

public class JWTUtil {

  public static final String SECRET = "mySecret1234";
  public static final String AUT_HEADER = "Authorization";
  public static final String PREFIX = "Bearer ";
  public static final long EXPIRE_ACCESS_TOKEN = 5 * 60 * 1000;
  public static final long EXPIRE_REFRESH_TOKEN = 15 * 60 * 1000;


}
