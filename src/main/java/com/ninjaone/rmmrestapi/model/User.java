package com.ninjaone.rmmrestapi.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name = "`user`")
public class User {
  @Id
  @Column(name = "email", nullable = false, unique = true)
  @JsonProperty("email")
  private String email;

  @JsonProperty(value = "password", access = JsonProperty.Access.WRITE_ONLY)
  private String password;

  public User() {
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }
}