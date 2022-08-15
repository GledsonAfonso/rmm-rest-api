package com.ninjaone.rmmrestapi.model;

import java.util.stream.Stream;

import com.fasterxml.jackson.annotation.JsonValue;

public enum ServiceType {
  ANTIVIRUS("antivirus"),
  BACKUP("backup"),
  PSA("psa"),
  SCREEN_SHARE("screen-share");

  private final String value;

  private ServiceType(final String value) {
    this.value = value;
  }

  @JsonValue
  public String getValue() {
    return value;
  }

  public static ServiceType of(String serviceTypeAsString) {
    return Stream.of(ServiceType.values())
      .filter(device -> device.getValue().equalsIgnoreCase(serviceTypeAsString))
      .findFirst()
      .orElseThrow(IllegalArgumentException::new);
  }
}
