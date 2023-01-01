package com.example.springsecurityv1.security.config;

import com.example.springsecurityv1.security.filters.JwtAuhtorizationFilter;
import com.example.springsecurityv1.security.filters.JwtAutheticationFilter;
import com.example.springsecurityv1.security.service.UserDetailsServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

  private UserDetailsServiceImpl userDetailsService;

  public SpringSecurityConfig(UserDetailsServiceImpl userDetailsService) {
    this.userDetailsService = userDetailsService;
  }
  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth.userDetailsService(userDetailsService);
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.csrf().disable();
    http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    http.headers().frameOptions().disable();
    http.authorizeRequests().antMatchers("/h2-console/**","/refresh-token/**","/login/**").permitAll();
    http.authorizeRequests().antMatchers(HttpMethod.POST,"/add-user","/add-role","/add-role-to-user").hasAuthority("ADMIN");
    http.authorizeRequests().antMatchers(HttpMethod.DELETE,"/user/**","/role/**").hasAuthority("ADMIN");
    http.authorizeRequests().antMatchers(HttpMethod.GET,"/users/**","/user/profile/**").hasAuthority("USER");
    http.authorizeRequests().anyRequest().authenticated();
    http.addFilter(new JwtAutheticationFilter(authenticationManagerBean()));
    http.addFilterBefore(new JwtAuhtorizationFilter(), UsernamePasswordAuthenticationFilter.class);
  }

  @Bean
  @Override
  public AuthenticationManager authenticationManagerBean() throws Exception {
    return super.authenticationManagerBean();
  }
}
