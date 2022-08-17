package com.ninjaone.rmmrestapi.configuration;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.auth0.jwt.exceptions.JWTVerificationException;

@Component
public class JwtFilter extends OncePerRequestFilter {
  private final MyUserDetailsService userDetailsService;
  private final JwtUtil jwtUtil;

  @Autowired
  public JwtFilter(MyUserDetailsService userDetailsService, JwtUtil jwtUtil) {
    this.userDetailsService = userDetailsService;
    this.jwtUtil = jwtUtil;
  }

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
    var authHeader = request.getHeader("Authorization");

    if (authHeader != null && !authHeader.isBlank()) {
      if (authHeader == null || authHeader.isBlank()) {
        response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid JWT Token in Bearer Header");
      } else {
        try {
          var email = jwtUtil.validateTokenAndRetrieveSubject(authHeader);
          var userDetails = userDetailsService.loadUserByUsername(email);
          var authToken = new UsernamePasswordAuthenticationToken(email, userDetails.getPassword(), userDetails.getAuthorities());
          
          if (SecurityContextHolder.getContext().getAuthentication() == null) {
            SecurityContextHolder.getContext().setAuthentication(authToken);
          }
        } catch (JWTVerificationException exc) {
          response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid JWT Token");
        }
      }
    }

    filterChain.doFilter(request, response);
  }
}
