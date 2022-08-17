package com.ninjaone.rmmrestapi.configuration;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;

@Component
public class JwtUtil {
  @Value("${general.secret}")
  private String secret;

  public String generateToken(String email) throws IllegalArgumentException, JWTCreationException {
    return JWT.create()
        .withSubject("User Details")
        .withClaim("email", email)
        .withIssuedAt(new Date())
        .withIssuer("NinjaOne")
        .sign(Algorithm.HMAC256(secret));
  }

  public String validateTokenAndRetrieveSubject(String token) throws JWTVerificationException {
    var verifier = JWT.require(Algorithm.HMAC256(secret))
        .withSubject("User Details")
        .withIssuer("NinjaOne")
        .build();
    var jwt = verifier.verify(token);

    return jwt.getClaim("email").asString();
  }
}
