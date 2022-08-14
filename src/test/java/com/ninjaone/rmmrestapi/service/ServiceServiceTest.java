package com.ninjaone.rmmrestapi.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
  public void beforeAll() {
    this.deviceEntity = new Device("new device", DeviceType.MAC);
    var deviceSaveResult = this.deviceService.save(this.deviceEntity);
    this.deviceEntity.setId(deviceSaveResult.getId());
  }

  @Test
  @DisplayName("should be able to retrieve all registered services")
  public void testGetAll() {
    var services = this.serviceService.getAll();
    assertEquals(11, services.size());
  }

  @Test
  @DisplayName("should be able to insert, read, and delete new data")
  public void testCRUDForNewData() {
    var service = new Service(ServiceType.ANTIVIRUS, this.deviceEntity);
    var saveReturn = this.serviceService.save(service);

    final Integer id = saveReturn.getId();
    service.setId(id);

    assertTrue(saveReturn.equals(service));

    var getAllResult = this.serviceService.getAll();
    assertEquals(12, getAllResult.size());

    var getByIdResultOptional = this.serviceService.getById(id);
    assertTrue(getByIdResultOptional.isPresent());

    var getByIdResult = getByIdResultOptional.get();
    assertEquals(service.getId(), getByIdResult.getId());
    assertEquals(service.getType(), getByIdResult.getType());

    var getByIdResultDevice = getByIdResult.getDevice();
    assertEquals(this.deviceEntity.getId(), getByIdResultDevice.getId());
    assertEquals(this.deviceEntity.getName(), getByIdResultDevice.getName());
    assertEquals(this.deviceEntity.getType(), getByIdResultDevice.getType());
    assertEquals(1, getByIdResultDevice.getServices().size());

    this.serviceService.deleteById(id);

    getAllResult = this.serviceService.getAll();
    assertEquals(11, getAllResult.size());

    getByIdResultOptional = this.serviceService.getById(id);
    assertTrue(getByIdResultOptional.isEmpty());
  }

  @Test
  @DisplayName("should not allow duplication of services")
  public void testDuplicateDevices() {
    var service1 = new Service(ServiceType.ANTIVIRUS, this.deviceEntity);
    var service2 = new Service(ServiceType.ANTIVIRUS, this.deviceEntity);
    var service3 = new Service(ServiceType.BACKUP, this.deviceEntity);

    var saveService1Result = this.serviceService.save(service1);
    assertThrows(Exception.class, () -> this.serviceService.save(service2));

    var saveService3Result = this.serviceService.save(service3);

    this.serviceService.deleteById(saveService1Result.getId());
    this.serviceService.deleteById(saveService3Result.getId());
  }
}
