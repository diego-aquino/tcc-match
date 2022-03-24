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
            HttpMethod.GET,
            "/api/notifications",
            "/api/tcc-subjects",
            "/api/tcc-guidance-requests",
            "/api/fields-of-study/professors",
            "/api/tcc-guidance-problems")
        .authenticated()
        .antMatchers(
            HttpMethod.POST,
            "/api/users/students",
            "/api/users/professors",
            "/api/fields-of-study",
            "/api/fields-of-study/select/*",
            "/api/tcc-subjects",
            "/api/tcc-guidance-requests",
            "/api/tcc-guidance-requests/review/*",
            "/api/tcc-guidances",
            "/api/tcc-guidances/finish/*",
            "/api/tcc-guidance-problems")
        .authenticated()
        .antMatchers(HttpMethod.PATCH, "/api/users/students/*", "/api/users/professors/*")
        .authenticated()
        .antMatchers(HttpMethod.DELETE, "/api/users/students/*", "/api/users/professors/*")
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
            UsernamePasswordAuthenticationFilter.class);
  }
}
