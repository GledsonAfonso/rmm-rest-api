package com.ninjaone.rmmrestapi.controller;

import java.util.Collections;
import java.util.Map;

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
  public Map<String, Object> register(@RequestBody User user) {
    var encodedPass = this.passwordEncoder.encode(user.getPassword());
    user.setPassword(encodedPass);
    user = this.userRepository.save(user);

    var token = this.jwtUtil.generateToken(user.getEmail());

    return Collections.singletonMap("jwt-token", token);
  }

  @PostMapping("/login")
  public Map<String, Object> login(@RequestBody UserDto user) {
    try {
      var authInputToken = new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword());
      this.authenticationManager.authenticate(authInputToken);

      var token = this.jwtUtil.generateToken(user.getEmail());

      return Collections.singletonMap("jwt-token", token);
    } catch (AuthenticationException exception) {
      throw new RuntimeException("Invalid Login Credentials");
    }
  }

}
