package com.ninjaone.rmmrestapi.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ninjaone.rmmrestapi.configuration.JwtUtil;
import com.ninjaone.rmmrestapi.database.UserRepository;
import com.ninjaone.rmmrestapi.dto.TokenDto;
import com.ninjaone.rmmrestapi.dto.UserDto;
import com.ninjaone.rmmrestapi.model.User;

@RestController
@RequestMapping("/auth")
public class AuthController {
  private final UserRepository userRepository;
  private final JwtUtil jwtUtil;
  private final AuthenticationManager authenticationManager;
  private final PasswordEncoder passwordEncoder;

  public AuthController(UserRepository userRepository, JwtUtil jwtUtil, AuthenticationManager authenticationManager, PasswordEncoder passwordEncoder) {
    this.userRepository = userRepository;
    this.jwtUtil = jwtUtil;
    this.authenticationManager = authenticationManager;
    this.passwordEncoder = passwordEncoder;
  }

  @PostMapping("/register")
  public ResponseEntity<TokenDto> register(@RequestBody User user) {
    var encodedPass = this.passwordEncoder.encode(user.getPassword());
    user.setPassword(encodedPass);
    user = this.userRepository.save(user);

    var token = this.jwtUtil.generateToken(user.getEmail());

    return ResponseEntity.ok(new TokenDto(token));
  }

  @PostMapping("/login")
  public ResponseEntity<TokenDto> login(@RequestBody UserDto user) {
    try {
      var authInputToken = new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword());
      this.authenticationManager.authenticate(authInputToken);

      var token = this.jwtUtil.generateToken(user.getEmail());

      return ResponseEntity.ok(new TokenDto(token));
    } catch (AuthenticationException | AccessDeniedException exception) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
  }

}
