package com.ninjaone.rmmrestapi.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.ninjaone.rmmrestapi.IntegrationTest;
import com.ninjaone.rmmrestapi.model.Device;
import com.ninjaone.rmmrestapi.model.DeviceType;

@IntegrationTest
public class DeviceServiceTest {
  @Autowired
  private DeviceService deviceService;

  @Test
  @DisplayName("should be able to retrieve all registered devices")
  public void testGetAll() {
    var devices = this.deviceService.getAll();
    assertEquals(3, devices.size());
  }

  @Test
  @DisplayName("should be able to insert, read, update and delete new data")
  public void testCRUDForNewData() {
    var device = new Device("new device for testing", DeviceType.MAC);
    var saveReturn = this.deviceService.save(device);

    final Integer id = saveReturn.getId();
    device.setId(id);

    assertTrue(saveReturn.equals(device));

    var getAllResult = this.deviceService.getAll();
    assertEquals(4, getAllResult.size());

    var getByIdResultOptional = this.deviceService.getById(id);
    assertTrue(getByIdResultOptional.isPresent());
    
    var getByIdResult = getByIdResultOptional.get();
    assertEquals(device.getId(), getByIdResult.getId());
    assertEquals(device.getName(), getByIdResult.getName());
    assertEquals(device.getType(), getByIdResult.getType());
    assertEquals(device.getServices().size(), getByIdResult.getServices().size());

    device.setName("new name");
    this.deviceService.save(device);
    
    getByIdResultOptional = this.deviceService.getById(id);
    assertTrue(getByIdResultOptional.isPresent());

    getByIdResult = getByIdResultOptional.get();
    assertEquals(device.getId(), getByIdResult.getId());
    assertEquals(device.getName(), getByIdResult.getName());
    assertEquals(device.getType(), getByIdResult.getType());
    assertEquals(device.getServices().size(), getByIdResult.getServices().size());

    this.deviceService.deleteById(id);

    getAllResult = this.deviceService.getAll();
    assertEquals(3, getAllResult.size());

    getByIdResultOptional = this.deviceService.getById(id);
    assertTrue(getByIdResultOptional.isEmpty());
  }

  @Test
  @DisplayName("should not allow duplication of devices")
  public void testDuplicateDevices() {
    var device1 = new Device("new device for testing", DeviceType.MAC);
    var device2 = new Device("new device for testing", DeviceType.MAC);
    var device3 = new Device("new device for testing", DeviceType.WINDOWS);

    var saveDevice1Result = this.deviceService.save(device1);
    assertThrows(Exception.class, () -> this.deviceService.save(device2));

    var saveDevice3Result = this.deviceService.save(device3);

    this.deviceService.deleteById(saveDevice1Result.getId());
    this.deviceService.deleteById(saveDevice3Result.getId());
  }
}
