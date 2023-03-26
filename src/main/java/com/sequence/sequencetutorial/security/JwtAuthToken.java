package com.sequence.sequencetutorial.security;

import com.sequence.sequencetutorial.auth.presentation.dto.LoginRequest;
import java.util.Collection;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

public class JwtAuthToken extends AbstractAuthenticationToken {

  private final Object principal;

  private String credentials;

  public JwtAuthToken(LoginRequest loginRequest) {
    super(null);
    super.setAuthenticated(false);

    this.principal = loginRequest;
    this.credentials = null;
  }

  JwtAuthToken(Object principal, String credentials,
    Collection<? extends GrantedAuthority> authorities) {
    super(authorities);
    super.setAuthenticated(true);

    this.principal = principal;
    this.credentials = credentials;
  }

  LoginRequest loginRequest() {
    return (LoginRequest) principal;
  }

  @Override
  public Object getPrincipal() {
    return principal;
  }

  @Override
  public String getCredentials() {
    return credentials;
  }

  public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
    if (isAuthenticated) {
      throw new IllegalArgumentException(
        "Cannot set this token to trusted - use constructor which takes a GrantedAuthority list instead");
    }
    super.setAuthenticated(false);
  }

  @Override
  public void eraseCredentials() {
    super.eraseCredentials();
    credentials = null;
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this)
      .append("principal", principal)
      .append("credentials", credentials)
      .toString();
  }
}
