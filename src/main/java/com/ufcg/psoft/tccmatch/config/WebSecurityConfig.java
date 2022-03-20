package com.ufcg.psoft.tccmatch.config;

import com.ufcg.psoft.tccmatch.services.sessions.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
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
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

  @Autowired
  private AuthenticationService authenticationService;

  @Override
  @Bean
  protected AuthenticationManager authenticationManager() throws Exception {
    return super.authenticationManager();
  }

  @Override
  protected void configure(AuthenticationManagerBuilder authenticationBuilder) throws Exception {
    authenticationBuilder
      .userDetailsService(authenticationService)
      .passwordEncoder(authenticationService.getPasswordEncoder());
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http
      .authorizeRequests()
      .antMatchers(
        HttpMethod.POST,
        "/api/users/students",
        "/api/users/professors",
        "/api/fields-of-study",
        "/api/tcc-subjects",
        "/api/tcc-guidance-requests"
      )
      .authenticated()
      .antMatchers(HttpMethod.GET, "/api/tcc-subjects", "api/tcc-guidance-requests")
      .authenticated()
      .anyRequest()
      .permitAll()
      .and()
      .headers()
      .frameOptions()
      .disable()
      .and()
      .csrf()
      .disable()
      .sessionManagement()
      .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
      .and()
      .addFilterBefore(
        new AuthenticationFilter(authenticationService),
        UsernamePasswordAuthenticationFilter.class
      );
  }
}
