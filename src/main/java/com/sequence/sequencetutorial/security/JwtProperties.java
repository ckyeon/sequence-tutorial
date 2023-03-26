package com.sequence.sequencetutorial.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.bind.ConstructorBinding;

@ConfigurationProperties(prefix = "jwt")
public class JwtProperties {

  private final String header;

  private final String issuer;

  private final String clientSecret;

  private final int expirySeconds;

  private final Algorithm algorithm;

  private final JWTVerifier jwtVerifier;

  @ConstructorBinding
  public JwtProperties(String header, String issuer, String clientSecret, int expirySeconds) {
    this.header = header;
    this.issuer = issuer;
    this.clientSecret = clientSecret;
    this.expirySeconds = expirySeconds;
    this.algorithm = Algorithm.HMAC512(clientSecret);
    this.jwtVerifier = JWT.require(algorithm)
      .withIssuer(issuer)
      .build();
  }

  public String getHeader() {
    return header;
  }

  public String getIssuer() {
    return issuer;
  }

  public String getClientSecret() {
    return clientSecret;
  }

  public int getExpirySeconds() {
    return expirySeconds;
  }

  public Algorithm getAlgorithm() {
    return algorithm;
  }

  public JWTVerifier getJwtVerifier() {
    return jwtVerifier;
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this)
      .append("header", header)
      .append("issuer", issuer)
      .append("clientSecret", clientSecret)
      .append("expirySeconds", expirySeconds)
      .append("algorithm", algorithm)
      .append("jwtVerifier", jwtVerifier)
      .toString();
  }
}
