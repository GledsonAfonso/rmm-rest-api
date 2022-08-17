package com.ninjaone.rmmrestapi.database;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ninjaone.rmmrestapi.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
  public Optional<User> findByEmail(String email);
}
