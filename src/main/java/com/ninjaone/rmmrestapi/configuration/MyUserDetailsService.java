package com.ninjaone.rmmrestapi.configuration;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.ninjaone.rmmrestapi.database.UserRepository;

@Component
public class MyUserDetailsService implements UserDetailsService {
  @Autowired
  private UserRepository userRepository;

  @Override
  public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    var userOptional = this.userRepository.findByEmail(email);

    if (userOptional.isEmpty()) {
      throw new UsernameNotFoundException("Could not findUser with email = " + email);
    }

    var user = userOptional.get();

    return new User(
        email,
        user.getPassword(),
        Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER")));
  }
}
