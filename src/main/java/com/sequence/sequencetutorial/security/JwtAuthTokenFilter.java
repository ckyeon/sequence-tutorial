package com.sequence.sequencetutorial.security;

import static java.util.Objects.nonNull;

import com.sequence.sequencetutorial.auth.domain.Role;
import com.sequence.sequencetutorial.security.Jwt.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.regex.Pattern;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

@Component
public class JwtAuthTokenFilter extends GenericFilterBean {

  private static final Pattern BEARER = Pattern.compile("^Bearer$", Pattern.CASE_INSENSITIVE);

  private final Logger log = LoggerFactory.getLogger(getClass());

  private final Jwt jwt;

  private final JwtProperties jwtProperties;

  public JwtAuthTokenFilter(Jwt jwt, JwtProperties jwtProperties) {
    this.jwt = jwt;
    this.jwtProperties = jwtProperties;
  }

  @Override
  public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
    throws IOException, ServletException {
    HttpServletRequest request = (HttpServletRequest) req;
    HttpServletResponse response = (HttpServletResponse) res;

    if (SecurityContextHolder.getContext().getAuthentication() == null) {
      String accessToken = obtainAccessToken(request);
      if (accessToken != null) {
        Claims claims = jwt.verify(accessToken);
        log.debug("Jwt parse result: {}", claims);

        Long id = claims.id;
        Role role = claims.role;

        if (nonNull(id) && nonNull(role)) {
          WebAuthenticationDetails webAuthDetails = new WebAuthenticationDetailsSource()
            .buildDetails(request);

          JwtAuth jwtAuth = new JwtAuth(id, role);
          JwtAuthToken jwtAuthToken = new JwtAuthToken(jwtAuth, null,
            AuthorityUtils.createAuthorityList(role.name()));
          jwtAuthToken.setDetails(webAuthDetails);
          SecurityContextHolder.getContext().setAuthentication(jwtAuthToken);
        }
      }
    } else {
      log.debug(
        "SecurityContextHolder not populated with security token, as it already contained: '{}'",
        SecurityContextHolder.getContext().getAuthentication());
    }

    chain.doFilter(request, response);
  }

  private String obtainAccessToken(HttpServletRequest request) {
    String token = request.getHeader(jwtProperties.getHeader());

    if (token == null) {
      return null;
    }

    if (log.isDebugEnabled()) {
      log.debug("Jwt authorization api detected: {}", token);
    }

    token = URLDecoder.decode(token, StandardCharsets.UTF_8);
    String[] parts = token.split(" ");
    if (parts.length != 2) {
      return null;
    }

    String header = parts[0];
    String accessToken = parts[1];
    return BEARER.matcher(header).matches() ? accessToken : null;
  }
}
