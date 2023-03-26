package com.sequence.sequencetutorial.auth.presentation.dto;

import static com.google.common.base.Preconditions.checkArgument;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class LoginRequest {

  private final String email;

  private final String password;

  public LoginRequest(String email, String password) {
    checkArgument(email != null, "email must be provided.");
    checkArgument(password != null, "password must be provided.");

    this.email = email;
    this.password = password;
  }

  public String getEmail() {
    return email;
  }

  public String getPassword() {
    return password;
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this)
      .append("email", email)
      .append("password", password)
      .toString();
  }
}
