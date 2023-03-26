package com.sequence.sequencetutorial.auth.application;

import static com.google.common.base.Preconditions.checkArgument;

import com.sequence.sequencetutorial.auth.presentation.dto.JoinRequest;
import com.sequence.sequencetutorial.auth.presentation.dto.LoginRequest;
import com.sequence.sequencetutorial.user.domain.User;
import com.sequence.sequencetutorial.user.domain.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthService {

  private final UserRepository userRepository;

  private final PasswordEncoder passwordEncoder;

  public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
    this.userRepository = userRepository;
    this.passwordEncoder = passwordEncoder;
  }

  @Transactional
  public void join(JoinRequest joinRequest) {
    checkArgument(joinRequest != null, "join request must be provided.");

    userRepository.findByEmail(joinRequest.getEmail())
      .ifPresentOrElse(
        (user) -> {
          throw new RuntimeException("이미 가입한 유저입니다.");
        },
        () -> {
          User user = joinRequest.toEntity(passwordEncoder);
          userRepository.save(user);
        });
  }

  @Transactional
  public User login(LoginRequest loginRequest) {
    checkArgument(loginRequest != null, "login request must be provided.");

    User user = userRepository.findByEmail(loginRequest.getEmail())
      .orElseThrow(() -> new RuntimeException("없는 유저입니다."));

    if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
      throw new IllegalArgumentException("Bad password");
    }

    return user;
  }
}
