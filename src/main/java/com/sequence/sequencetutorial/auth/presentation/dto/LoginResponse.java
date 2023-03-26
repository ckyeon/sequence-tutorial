package com.sequence.sequencetutorial.auth.presentation.dto;

import static com.google.common.base.Preconditions.checkArgument;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class LoginResponse {

  private final String accessToken;

  private final String refreshToken;

  public LoginResponse(String accessToken, String refreshToken) {
    checkArgument(accessToken != null, "accessToken must be provided.");
    checkArgument(refreshToken != null, "refreshToken must be provided.");

    this.accessToken = accessToken;
    this.refreshToken = refreshToken;
  }

  public String getAccessToken() {
    return accessToken;
  }

  public String getRefreshToken() {
    return refreshToken;
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this)
      .append("accessToken", accessToken)
      .append("refreshToken", refreshToken)
      .toString();
  }
}
