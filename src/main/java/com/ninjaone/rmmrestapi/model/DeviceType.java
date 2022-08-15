package com.ninjaone.rmmrestapi.model;

import java.util.stream.Stream;

import com.fasterxml.jackson.annotation.JsonValue;

public enum DeviceType {
  WINDOWS("windows"),
  MAC("mac");

  private final String value;

  private DeviceType(final String value) {
    this.value = value;
  }

  @JsonValue
  public String getValue() {
    return value;
  }

  public static DeviceType of(String deviceTypeAsString) {
    return Stream.of(DeviceType.values())
      .filter(device -> device.getValue().equalsIgnoreCase(deviceTypeAsString))
      .findFirst()
      .orElseThrow(IllegalArgumentException::new);
  }
}
