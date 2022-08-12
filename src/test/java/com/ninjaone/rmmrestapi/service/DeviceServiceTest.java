package com.ninjaone.rmmrestapi.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.ninjaone.rmmrestapi.database.DeviceRepository;
import com.ninjaone.rmmrestapi.model.Device;
import com.ninjaone.rmmrestapi.model.DeviceType;
import com.ninjaone.rmmrestapi.model.Service;

@ExtendWith(MockitoExtension.class)
public class DeviceServiceTest {
    public static final Integer ID = 12345;

    @Mock
    private DeviceRepository repository;

    @InjectMocks
    private DeviceService testObject;

    private Device entity;

    @BeforeEach
    void setup() {
        entity = new Device(ID, "device 12345", DeviceType.WINDOWS, new ArrayList<Service>());
    }

    @Test
    void getDeviceData() {
        when(repository.findById(ID)).thenReturn(Optional.of(entity));
        Optional<Device> entityOptional = testObject.getById(ID);
        Device actualEntity = entityOptional.orElse(null);
        assert actualEntity != null;
        assertEquals(entity.getName(), actualEntity.getName());
        assertEquals(entity.getType().toString(), actualEntity.getType().toString());
        assertEquals(entity.getServices().size(), actualEntity.getServices().size());
    }

    @Test
    void saveDeviceData() {
        when(repository.save(entity)).thenReturn(entity);
        assertEquals(entity, testObject.save(entity));
    }

    @Test
    void deleteDeviceData() {
        doNothing().when(repository).deleteById(ID);
        testObject.deleteById(ID);
        Mockito.verify(repository, times(1)).deleteById(ID);
    }
}
