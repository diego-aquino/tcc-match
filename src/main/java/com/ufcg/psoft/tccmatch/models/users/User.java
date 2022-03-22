package com.ufcg.psoft.tccmatch.models.users;

import java.util.Collection;
import java.util.Collections;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Transient;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class User implements UserDetails {

  public enum Type {
    STUDENT,
    PROFESSOR,
    COORDINATOR,
  }

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Transient
  private Type type;

  @Column(unique = true)
  private String email;

  private String encodedPassword;

  private String name;
  
  protected User() {}

  protected User(Type type) {
    this(type, null, null, null);
  }

  protected User(Type type, String email, String encodedPassword, String name) {
    this.type = type;
    this.email = email;
    this.encodedPassword = encodedPassword;
    this.name = name;
  }

  public Long getId() {
    return id;
  }

  public Type getType() {
    return type;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  @Override
  public String getUsername() {
    return email;
  }

  @Override
  public String getPassword() {
    return encodedPassword;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Collection<? extends GrantedAuthority> getAuthorities() {
    return Collections.emptyList();
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }

}