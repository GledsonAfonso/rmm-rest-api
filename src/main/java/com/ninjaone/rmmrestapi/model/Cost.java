package com.ninjaone.rmmrestapi.model;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Cost {
  @JsonProperty("devices_cost")
  private BigDecimal devicesCost;

  @JsonProperty("antivirus_cost")
  private BigDecimal antivirusCost;

  @JsonProperty("backup_cost")
  private BigDecimal backupCost;

  @JsonProperty("psa_cost")
  private BigDecimal psaCost;

  @JsonProperty("screen_share")
  private BigDecimal screenShare;

  @JsonProperty("total_cost")
  private BigDecimal totalCost;

  public Cost() {
    this.devicesCost = BigDecimal.ZERO;
    this.antivirusCost = BigDecimal.ZERO;
    this.backupCost = BigDecimal.ZERO;
    this.psaCost = BigDecimal.ZERO;
    this.screenShare = BigDecimal.ZERO;
    this.totalCost = BigDecimal.ZERO;
  }

  public void addToDevicesCost(BigDecimal devicesCost) {
    this.devicesCost = this.devicesCost.add(devicesCost);
    this.totalCost = this.totalCost.add(devicesCost);
  }

  public void addToAntivirusCost(BigDecimal antivirusCost) {
    this.antivirusCost = this.antivirusCost.add(antivirusCost);
    this.totalCost = this.totalCost.add(antivirusCost);
  }

  public void addToBackupCost(BigDecimal backupCost) {
    this.backupCost = this.backupCost.add(backupCost);
    this.totalCost = this.totalCost.add(backupCost);
  }

  public void addToPsaCost(BigDecimal psaCost) {
    this.psaCost = this.psaCost.add(psaCost);
    this.totalCost = this.totalCost.add(psaCost);
  }

  public void addToScreenShare(BigDecimal screenShare) {
    this.screenShare = this.screenShare.add(screenShare);
    this.totalCost = this.totalCost.add(screenShare);
  }

  public BigDecimal getDevicesCost() {
    return devicesCost;
  }

  public BigDecimal getAntivirusCost() {
    return antivirusCost;
  }

  public BigDecimal getBackupCost() {
    return backupCost;
  }

  public BigDecimal getPsaCost() {
    return psaCost;
  }

  public BigDecimal getScreenShare() {
    return screenShare;
  }

  public BigDecimal getTotalCost() {
    return totalCost;
  }
}
