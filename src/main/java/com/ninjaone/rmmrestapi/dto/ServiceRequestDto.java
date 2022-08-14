package com.ninjaone.rmmrestapi.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ninjaone.rmmrestapi.model.ServiceType;

public class ServiceRequestDto {
  @JsonProperty("type")
  private ServiceType type;

  @JsonProperty("device")
  private int deviceId;

  public ServiceType getType() {
    return type;
  }

  public void setType(ServiceType type) {
    this.type = type;
  }

  public int getDeviceId() {
    return deviceId;
  }

  public void setDeviceId(int deviceId) {
    this.deviceId = deviceId;
  }
}
