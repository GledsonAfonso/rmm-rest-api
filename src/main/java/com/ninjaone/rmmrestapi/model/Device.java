package com.ninjaone.rmmrestapi.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ninjaone.rmmrestapi.dto.DeviceRequestDto;
import com.ninjaone.rmmrestapi.dto.ServiceRequestDto;

@Entity
@Table(name = "device", uniqueConstraints = @UniqueConstraint(columnNames = { "name", "type" }))
public class Device {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", updatable = false, nullable = false, unique = true)
  @JsonProperty("id")
  private Integer id;

  @Column(name = "name")
  @JsonProperty("name")
  private String name;

  @Column(name = "type")
  @JsonProperty("type")
  private DeviceType type;

  @OneToMany(mappedBy = "device", fetch = FetchType.EAGER)
  @Column(name = "service_id")
  @JsonProperty("services")
  private List<Service> services = new ArrayList<>();

  public Device() {
  }

  public Device(Integer id, String name, DeviceType type, List<Service> services) {
    this.id = id;
    this.name = name;
    this.type = type;
    this.services = services;
  }

  public Device(String name, DeviceType type) {
    this.name = name;
    this.type = type;
    this.services = new ArrayList<>();
  }

  public Device(DeviceRequestDto dto) {
    this.name = dto.getName();
    this.type = dto.getType();
    this.services = new ArrayList<>();
  }

  public Device(ServiceRequestDto request) {
    this.id = request.getDeviceId();
  }

  public void setDtoInfo(DeviceRequestDto dto) {
    this.name = dto.getName();
    this.type = dto.getType();
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public DeviceType getType() {
    return type;
  }

  public List<Service> getServices() {
    return Collections.unmodifiableList(services);
  }
}
