package com.ninjaone.rmmrestapi.model;

public enum ServiceType {
  ANTIVIRUS("antivirus"),
  BACKUP("backup"),
  PSA("psa"),
  SCREEN_SHARE("screen-share");

  private final String value;

  ServiceType(final String value) {
    this.value = value;
  }

  @Override
  public String toString() {
    return this.value;
  }
}
