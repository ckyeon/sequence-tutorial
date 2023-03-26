package com.sequence.sequencetutorial.auth.presentation;

import com.sequence.sequencetutorial.auth.application.AuthService;
import com.sequence.sequencetutorial.auth.presentation.dto.JoinRequest;
import com.sequence.sequencetutorial.auth.presentation.dto.LoginRequest;
import com.sequence.sequencetutorial.auth.presentation.dto.LoginResponse;
import com.sequence.sequencetutorial.security.JwtAuthToken;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

  private final AuthService authService;

  private final AuthenticationManager authenticationManager;

  public AuthController(AuthenticationManager authenticationManager, AuthService authService) {
    this.authenticationManager = authenticationManager;
    this.authService = authService;
  }

  @PostMapping("/join")
  public ResponseEntity<Void> join(@RequestBody JoinRequest joinRequest) {
    authService.join(joinRequest);
    return ResponseEntity.ok(null);
  }

  @PostMapping("/login")
  public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest) {
    JwtAuthToken jwtAuthToken = new JwtAuthToken(loginRequest);
    Authentication authentication = authenticationManager.authenticate(jwtAuthToken);
    SecurityContextHolder.getContext().setAuthentication(jwtAuthToken);
    return ResponseEntity.ok((LoginResponse) authentication.getDetails());
  }
}
