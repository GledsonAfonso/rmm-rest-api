package com.ninjaone.rmmrestapi.service;

import java.math.BigDecimal;

import org.springframework.stereotype.Service;

import com.ninjaone.rmmrestapi.model.Cost;

@Service
public class CostService {
  private final DeviceService deviceService;

  private final BigDecimal DEVICE_UNIT_BASE_COST = BigDecimal.valueOf(4);
  private final BigDecimal WINDOWS_ANTIVIRUS_BASE_COST = BigDecimal.valueOf(5);
  private final BigDecimal MAC_ANTIVIRUS_BASE_COST = BigDecimal.valueOf(7);
  private final BigDecimal BACKUP_BASE_COST = BigDecimal.valueOf(3);
  private final BigDecimal PSA_BASE_COST = BigDecimal.valueOf(2);
  private final BigDecimal SCREEN_SHARE_BASE_COST = BigDecimal.valueOf(1);

  public CostService(DeviceService deviceService) {
    this.deviceService = deviceService;
  }

  public Cost getCurrentMonthlyCost() {
    var devices = deviceService.getAll();
    
    var cost = new Cost();
    cost.addToDevicesCost(BigDecimal.valueOf(devices.size()).multiply(DEVICE_UNIT_BASE_COST));

    devices.forEach(device -> {
      device.getServices().forEach(service -> {
        switch (service.getType()) {
          case ANTIVIRUS:
            switch (device.getType()) {
              case WINDOWS:
                cost.addToAntivirusCost(WINDOWS_ANTIVIRUS_BASE_COST);
                break;
              case MAC:
                cost.addToAntivirusCost(MAC_ANTIVIRUS_BASE_COST);
                break;
            }
            break;
          case BACKUP:
            cost.addToBackupCost(BACKUP_BASE_COST);
            break;
          case PSA:
            cost.addToPsaCost(PSA_BASE_COST);
            break;
          case SCREEN_SHARE:
            cost.addToScreenShare(SCREEN_SHARE_BASE_COST);
            break;
        }
      });
    });

    return cost;
  }
}
