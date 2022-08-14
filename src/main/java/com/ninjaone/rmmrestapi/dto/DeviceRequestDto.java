package com.ninjaone.rmmrestapi.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ninjaone.rmmrestapi.model.DeviceType;

public class DeviceRequestDto {
  @JsonProperty("name")
  private String name;

  @JsonProperty("type")
  private DeviceType type;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public DeviceType getType() {
    return type;
  }

  public void setType(DeviceType type) {
    this.type = type;
  }
}
