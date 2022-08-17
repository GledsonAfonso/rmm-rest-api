package com.ninjaone.rmmrestapi.controller;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ninjaone.rmmrestapi.database.UserRepository;
import com.ninjaone.rmmrestapi.model.User;

@RestController
@RequestMapping("/user")
public class UserController {
  private final UserRepository repository;

  public UserController(UserRepository repository) {
    this.repository = repository;
  }

  @GetMapping("/info")
  public User getUserDetails() {
    var email = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    return repository.findByEmail(email).get();
  }

}
