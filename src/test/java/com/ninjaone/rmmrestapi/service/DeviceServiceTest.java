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
  private DeviceService service;

  @Test
  @DisplayName("should be able to retrieve all registered devices")
  public void testGetAll() {
    var devices = service.getAll();
    assertEquals(3, devices.size());
  }

  @Test
  @DisplayName("should be able to insert, read, update and delete new data")
  public void testCRUDForNewData() {
    var device = new Device("new device for testing", DeviceType.MAC);
    var saveReturn = service.save(device);

    final Integer id = saveReturn.getId();
    device.setId(id);

    assertTrue(saveReturn.equals(device));

    var getAllResult = service.getAll();
    assertEquals(4, getAllResult.size());

    var getByIdResultOptional = service.getById(id);
    assertTrue(getByIdResultOptional.isPresent());
    var getByIdResult = getByIdResultOptional.get();
    assertEquals(getByIdResult.getId(), device.getId());
    assertEquals(getByIdResult.getName(), device.getName());
    assertEquals(getByIdResult.getType(), device.getType());
    assertEquals(getByIdResult.getServices().size(), device.getServices().size());

    device.setName("new name");
    service.save(device);
    getByIdResultOptional = service.getById(id);
    assertTrue(getByIdResultOptional.isPresent());
    getByIdResult = getByIdResultOptional.get();
    assertEquals(getByIdResult.getId(), device.getId());
    assertEquals(getByIdResult.getName(), device.getName());
    assertEquals(getByIdResult.getType(), device.getType());
    assertEquals(getByIdResult.getServices().size(), device.getServices().size());

    service.deleteById(id);

    getAllResult = service.getAll();
    assertEquals(3, getAllResult.size());

    getByIdResultOptional = service.getById(id);
    assertTrue(getByIdResultOptional.isEmpty());
  }

  @Test
  @DisplayName("should not allow duplication of devices")
  public void testDuplicateDevices() {
    var device1 = new Device("new device for testing", DeviceType.MAC);
    var device2 = new Device("new device for testing", DeviceType.MAC);
    var device3 = new Device("new device for testing", DeviceType.WINDOWS);

    var saveDevice1Result = service.save(device1);
    assertThrows(Exception.class, () -> service.save(device2));

    var saveDevice3Result = service.save(device3);

    service.deleteById(saveDevice1Result.getId());
    service.deleteById(saveDevice3Result.getId());
  }
}
