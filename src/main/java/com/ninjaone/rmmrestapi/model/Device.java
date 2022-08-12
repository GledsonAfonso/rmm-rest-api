package com.ninjaone.rmmrestapi.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "DEVICE")
public class Device {
  @Id
  @GeneratedValue
  @Column(name = "id")
  private Integer id;

  @Column(name = "name")
  private String name;

  @Enumerated(EnumType.STRING)
  @Column(name = "type")
  private DeviceType type;

  @OneToMany(mappedBy = "device")
  @Column(name = "service_id")
  private List<Service> services = new ArrayList<>();

  public Device() {
  }

  public Device(Integer id, String name, DeviceType type, List<Service> services) {
    this.id = id;
    this.name = name;
    this.type = type;
    this.services = services;
  }

  public Integer getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public DeviceType getType() {
    return type;
  }

  public List<Service> getServices() {
    return Collections.unmodifiableList(services);
  }
}
