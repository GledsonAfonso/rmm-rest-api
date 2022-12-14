package com.ninjaone.rmmrestapi.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "service", uniqueConstraints = @UniqueConstraint(columnNames = { "type", "device_id" }))
public class Service {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", updatable = false, nullable = false, unique = true)
  private Integer id;

  @Column(name = "type", nullable = false)
  private ServiceType type;

  @ManyToOne
  @JsonIgnore
  private Device device;

  public Service() {
  }

  public Service(Integer id, ServiceType type) {
    this.id = id;
    this.type = type;
  }

  public Service(ServiceType type, Device device) {
    this.type = type;
    this.device = device;
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public ServiceType getType() {
    return type;
  }

  public void setType(ServiceType type) {
    this.type = type;
  }

  public Device getDevice() {
    return device;
  }
}
