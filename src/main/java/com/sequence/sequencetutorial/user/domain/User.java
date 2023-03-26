package com.sequence.sequencetutorial.user.domain;

import static com.google.common.base.Preconditions.checkArgument;

import com.sequence.sequencetutorial.auth.domain.Role;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String email;

  private String password;

  @Enumerated(EnumType.STRING)
  private Role role;

  protected User() {

  }

  public User(String email, String password) {
    this(null, email, password, Role.ROLE_USER);
  }

  public User(Long id, String email, String password, Role role) {
    checkArgument(email != null, "email must be provided.");
    checkArgument(password != null, "password must be provided.");
    checkArgument(role != null, "role must be provided.");

    this.id = id;
    this.email = email;
    this.password = password;
    this.role = role;
  }

  public Long getId() {
    return id;
  }

  public String getEmail() {
    return email;
  }

  public String getPassword() {
    return password;
  }

  public Role getRole() {
    return role;
  }
}
