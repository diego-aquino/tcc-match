package com.ufcg.psoft.tccmatch.services.sessions;

import com.ufcg.psoft.tccmatch.exceptions.users.ForbiddenUserTypeException;
import com.ufcg.psoft.tccmatch.models.users.User;
import com.ufcg.psoft.tccmatch.services.users.UserService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import java.util.Arrays;
import java.util.Date;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService implements UserDetailsService {

  @Value("${jwt.secret}")
  private String jwtSecret;

  @Value("${jwt.expirationTime}")
  private String jwtExpirationTime;

  @Autowired
  private UserService userService;

  private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

  public PasswordEncoder getPasswordEncoder() {
    return passwordEncoder;
  }

  public String encodePassword(String password) {
    return passwordEncoder.encode(password);
  }

  public String generateToken(Authentication authentication) {
    User user = (User) authentication.getPrincipal();

    long currentTime = new Date().getTime();
    Date expiresAt = new Date(currentTime + Long.parseLong(jwtExpirationTime));

    return Jwts
      .builder()
      .setIssuer("tccmatch")
      .setSubject(user.getId().toString())
      .setIssuedAt(new Date())
      .setExpiration(expiresAt)
      .signWith(SignatureAlgorithm.HS256, jwtSecret)
      .compact();
  }

  public boolean isValidToken(String token) {
    try {
      Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token);
      return true;
    } catch (
      ExpiredJwtException
      | UnsupportedJwtException
      | MalformedJwtException
      | SignatureException
      | IllegalArgumentException exception
    ) {
      return false;
    }
  }

  public Optional<User> getUserFromToken(String token) {
    Long userId = getUserIdFromToken(token);
    return userService.findUserById(userId);
  }

  public Long getUserIdFromToken(String token) {
    Claims tokenBody = Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody();
    return Long.valueOf(tokenBody.getSubject());
  }

  public void ensureUserTypes(User.Type... userTypes) {
    User user = getAuthenticatedUser();

    boolean userHasAnAllowedType = Arrays
      .stream(userTypes)
      .anyMatch(type -> type == user.getType());

    if (!userHasAnAllowedType) {
      throw new ForbiddenUserTypeException(user.getType());
    }
  }

  public User getAuthenticatedUser() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    User user = (User) authentication.getPrincipal();
    return user;
  }

  @Override
  public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    Optional<User> optionalUser = userService.findUserByEmail(email);

    if (optionalUser.isEmpty()) {
      throw new UsernameNotFoundException(String.format("User with email '%s' not found", email));
    }

    User user = optionalUser.get();
    return user;
  }
}
