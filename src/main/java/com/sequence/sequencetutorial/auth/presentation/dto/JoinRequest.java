package com.sequence.sequencetutorial.auth.presentation.dto;

import static com.google.common.base.Preconditions.checkArgument;

import com.sequence.sequencetutorial.user.domain.User;
import org.springframework.security.crypto.password.PasswordEncoder;

public class JoinRequest {

  private final String email;

  private final String password;

  public JoinRequest(String email, String password) {
    checkArgument(email != null, "email must be provided.");
    checkArgument(password != null, "password must be provided.");

    this.email = email;
    this.password = password;
  }

  public User toEntity(PasswordEncoder passwordEncoder) {
    return new User(email, passwordEncoder.encode(password));
  }

  public String getEmail() {
    return email;
  }

  public String getPassword() {
    return password;
  }
}
