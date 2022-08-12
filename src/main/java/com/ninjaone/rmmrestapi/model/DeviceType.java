package com.ninjaone.rmmrestapi.model;

public enum DeviceType {
  WINDOWS("windows"),
  MAC("mac");

  private final String value;

  DeviceType(final String value) {
    this.value = value;
  }

  @Override
  public String toString() {
    return this.value;
  }
}
