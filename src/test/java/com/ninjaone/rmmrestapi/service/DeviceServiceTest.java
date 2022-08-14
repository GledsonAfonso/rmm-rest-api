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
  void testGetAll() {
    var devices = deviceService.getAll();
    assertEquals(3, devices.size());
  }

  @Test
  @DisplayName("should be able to insert, read, update and delete new data")
  void testCRUDForNewData() {
    var device = new Device("new device for testing", DeviceType.MAC);
    var saveReturn = deviceService.save(device);

    final Integer id = saveReturn.getId();
    device.setId(id);

    assertTrue(saveReturn.equals(device));

    var getAllResult = deviceService.getAll();
    assertEquals(4, getAllResult.size());

    var getByIdResultOptional = deviceService.getById(id);
    assertTrue(getByIdResultOptional.isPresent());
    
    var getByIdResult = getByIdResultOptional.get();
    assertEquals(device.getId(), getByIdResult.getId());
    assertEquals(device.getName(), getByIdResult.getName());
    assertEquals(device.getType(), getByIdResult.getType());
    assertEquals(device.getServices().size(), getByIdResult.getServices().size());

    device.setName("new name");
    deviceService.save(device);
    
    getByIdResultOptional = deviceService.getById(id);
    assertTrue(getByIdResultOptional.isPresent());

    getByIdResult = getByIdResultOptional.get();
    assertEquals(device.getId(), getByIdResult.getId());
    assertEquals(device.getName(), getByIdResult.getName());
    assertEquals(device.getType(), getByIdResult.getType());
    assertEquals(device.getServices().size(), getByIdResult.getServices().size());

    deviceService.deleteById(id);

    getAllResult = deviceService.getAll();
    assertEquals(3, getAllResult.size());

    getByIdResultOptional = deviceService.getById(id);
    assertTrue(getByIdResultOptional.isEmpty());
  }

  @Test
  @DisplayName("should not allow duplication of devices")
  void testDuplicateDevices() {
    var device1 = new Device("new device for testing", DeviceType.MAC);
    var device2 = new Device("new device for testing", DeviceType.MAC);
    var device3 = new Device("new device for testing", DeviceType.WINDOWS);

    var saveDevice1Result = deviceService.save(device1);
    assertThrows(Exception.class, () -> deviceService.save(device2));

    var saveDevice3Result = deviceService.save(device3);

    deviceService.deleteById(saveDevice1Result.getId());
    deviceService.deleteById(saveDevice3Result.getId());
  }
}
