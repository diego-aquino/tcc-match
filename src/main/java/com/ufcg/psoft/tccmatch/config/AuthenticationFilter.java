package com.ufcg.psoft.tccmatch.config;

import com.ufcg.psoft.tccmatch.models.users.User;
import com.ufcg.psoft.tccmatch.services.sessions.AuthenticationService;
import java.io.IOException;
import java.util.Optional;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

public class AuthenticationFilter extends OncePerRequestFilter {

  private AuthenticationService authenticationService;

  public AuthenticationFilter(AuthenticationService authenticationService) {
    this.authenticationService = authenticationService;
  }

  @Override
  protected void doFilterInternal(
    HttpServletRequest request,
    HttpServletResponse response,
    FilterChain filterChain
  ) throws ServletException, IOException {
    authenticateUserToSecurityContext(request);
    filterChain.doFilter(request, response);
  }

  private void authenticateUserToSecurityContext(HttpServletRequest request) {
    String token = getTokenFromHeader(request);

    boolean tokenInValid = authenticationService.isValidToken(token);
    if (!tokenInValid) return;

    Optional<User> optionalUser = authenticationService.getUserFromToken(token);
    if (optionalUser.isEmpty()) return;

    User user = optionalUser.get();
    saveUserToSecurityContext(user);
  }

  private String getTokenFromHeader(HttpServletRequest request) {
    String token = request.getHeader("Authorization");

    if (token == null || token.isBlank() || !token.startsWith("Bearer ")) {
      return null;
    }

    String tokenWithoutBearer = token.substring(7, token.length());
    return tokenWithoutBearer;
  }

  private void saveUserToSecurityContext(User user) {
    SecurityContextHolder
      .getContext()
      .setAuthentication(
        new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities())
      );
  }
}
