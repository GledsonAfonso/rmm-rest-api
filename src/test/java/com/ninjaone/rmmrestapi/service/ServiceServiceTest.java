package com.ninjaone.rmmrestapi.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.ninjaone.rmmrestapi.IntegrationTest;
import com.ninjaone.rmmrestapi.model.Device;
import com.ninjaone.rmmrestapi.model.DeviceType;
import com.ninjaone.rmmrestapi.model.Service;
import com.ninjaone.rmmrestapi.model.ServiceType;

@IntegrationTest
public class ServiceServiceTest {
  @Autowired
  private DeviceService deviceService;

  @Autowired
  private ServiceService serviceService;

  private Device deviceEntity;

  @BeforeAll
  void beforeAll() {
    deviceEntity = new Device("new device", DeviceType.MAC);
    var deviceSaveResult = deviceService.save(deviceEntity);
    deviceEntity.setId(deviceSaveResult.getId());
  }

  @AfterAll
  void afterAll() {
    deviceService.deleteById(deviceEntity.getId());
  }

  @Test
  @DisplayName("should be able to retrieve all registered services")
  void testGetAll() {
    var services = serviceService.getAll();
    assertEquals(11, services.size());
  }

  @Test
  @DisplayName("should be able to insert, read, and delete new data")
  void testCRUDForNewData() {
    var service = new Service(ServiceType.ANTIVIRUS, deviceEntity);
    var saveReturn = serviceService.save(service);

    final Integer id = saveReturn.getId();
    service.setId(id);

    assertTrue(saveReturn.equals(service));

    var getAllResult = serviceService.getAll();
    assertEquals(12, getAllResult.size());

    var getByIdResultOptional = serviceService.getById(id);
    assertTrue(getByIdResultOptional.isPresent());

    var getByIdResult = getByIdResultOptional.get();
    assertEquals(service.getId(), getByIdResult.getId());
    assertEquals(service.getType(), getByIdResult.getType());

    var getByIdResultDevice = getByIdResult.getDevice();
    assertEquals(deviceEntity.getId(), getByIdResultDevice.getId());
    assertEquals(deviceEntity.getName(), getByIdResultDevice.getName());
    assertEquals(deviceEntity.getType(), getByIdResultDevice.getType());
    assertEquals(1, getByIdResultDevice.getServices().size());

    serviceService.deleteById(id);

    getAllResult = serviceService.getAll();
    assertEquals(11, getAllResult.size());

    getByIdResultOptional = serviceService.getById(id);
    assertTrue(getByIdResultOptional.isEmpty());
  }

  @Test
  @DisplayName("should not allow duplication of services")
  void testDuplicateDevices() {
    var service1 = new Service(ServiceType.ANTIVIRUS, deviceEntity);
    var service2 = new Service(ServiceType.ANTIVIRUS, deviceEntity);
    var service3 = new Service(ServiceType.BACKUP, deviceEntity);

    var saveService1Result = serviceService.save(service1);
    assertThrows(Exception.class, () -> serviceService.save(service2));

    var saveService3Result = serviceService.save(service3);

    serviceService.deleteById(saveService1Result.getId());
    serviceService.deleteById(saveService3Result.getId());
  }
}
