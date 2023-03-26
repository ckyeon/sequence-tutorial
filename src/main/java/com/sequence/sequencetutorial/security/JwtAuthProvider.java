package com.sequence.sequencetutorial.security;

import com.sequence.sequencetutorial.auth.application.AuthService;
import com.sequence.sequencetutorial.auth.presentation.dto.LoginRequest;
import com.sequence.sequencetutorial.auth.presentation.dto.LoginResponse;
import com.sequence.sequencetutorial.user.application.UserService;
import com.sequence.sequencetutorial.user.domain.User;
import org.apache.commons.lang3.ClassUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.webjars.NotFoundException;

@Component
public class JwtAuthProvider implements AuthenticationProvider {

  private final Jwt jwt;

  private final AuthService authService;

  public JwtAuthProvider(Jwt jwt, AuthService authService) {
    this.jwt = jwt;
    this.authService = authService;
  }

  @Override
  public boolean supports(Class<?> authentication) {
    return ClassUtils.isAssignable(JwtAuthToken.class, authentication);
  }

  @Override
  public Authentication authenticate(Authentication authentication) throws AuthenticationException {
    JwtAuthToken jwtAuthToken = (JwtAuthToken) authentication;
    return processUserLogin(jwtAuthToken.loginRequest());
  }

  private Authentication processUserLogin(LoginRequest loginRequest) {
    try {
      User user = authService.login(loginRequest);
      JwtAuth jwtAuth = new JwtAuth(user.getId(), user.getRole());
      JwtAuthToken jwtAuthToken = new JwtAuthToken(jwtAuth, null,
        AuthorityUtils.createAuthorityList(user.getRole().name()));

      String accessToken = jwt.accessToken(user);
      String refreshToken = jwt.refreshToken(user);
      jwtAuthToken.setDetails(new LoginResponse(accessToken, refreshToken));
      return jwtAuthToken;
    } catch (NotFoundException e) {
      throw new UsernameNotFoundException(e.getMessage());
    } catch (IllegalArgumentException e) {
      throw new BadCredentialsException(e.getMessage());
    } catch (DataAccessException e) {
      throw new AuthenticationServiceException(e.getMessage(), e);
    }
  }
}
