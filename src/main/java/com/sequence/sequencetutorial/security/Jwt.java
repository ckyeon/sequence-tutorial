package com.sequence.sequencetutorial.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.sequence.sequencetutorial.auth.domain.Role;
import com.sequence.sequencetutorial.user.domain.User;
import java.util.Date;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.stereotype.Component;

@Component
public class Jwt {

  private final JwtProperties properties;

  public Jwt(JwtProperties properties) {
    this.properties = properties;
  }

  public String accessToken(User user) {
    JWTCreator.Builder builder = JWT.create();

    Date now = new Date();
    int expirySeconds = properties.getExpirySeconds();
    if (expirySeconds > 0) {
      builder.withExpiresAt(new Date(now.getTime() + expirySeconds * 1_000L));
    }
    builder.withIssuedAt(now);
    builder.withIssuer(properties.getIssuer());
    builder.withClaim("id", user.getId());
    builder.withClaim("role", user.getRole().name());

    return builder.sign(properties.getAlgorithm());
  }

  public String refreshToken(User user) {
    // Todo: 리프레쉬 토큰 구현
    return "refresh token";
  }

  public Claims verify(String accessToken) {
    JWTVerifier jwtVerifier = properties.getJwtVerifier();
    return new Claims(jwtVerifier.verify(accessToken));
  }

  static public class Claims {

    Long id;
    Role role;
    Date iat;
    Date exp;

    private Claims() {
    }

    Claims(DecodedJWT decodedJWT) {
      Claim id = decodedJWT.getClaim("id");
      if (!id.isNull()) {
        this.id = id.asLong();
      }

      Claim role = decodedJWT.getClaim("role");
      if (!role.isNull()) {
        this.role = role.as(Role.class);
      }

      this.iat = decodedJWT.getIssuedAt();

      this.exp = decodedJWT.getExpiresAt();
    }

    @Override
    public String toString() {
      return new ToStringBuilder(this)
        .append("id", id)
        .append("role", role)
        .append("iat", iat)
        .append("exp", exp)
        .toString();
    }
  }
}
