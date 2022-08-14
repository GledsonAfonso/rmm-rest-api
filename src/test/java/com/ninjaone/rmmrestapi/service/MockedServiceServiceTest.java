package com.ninjaone.rmmrestapi.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.ninjaone.rmmrestapi.database.ServiceRepository;
import com.ninjaone.rmmrestapi.model.Service;
import com.ninjaone.rmmrestapi.model.ServiceType;

@ExtendWith(MockitoExtension.class)
public class MockedServiceServiceTest {
    public static final Integer ID = 12345;

    @Mock
    private ServiceRepository repository;

    @InjectMocks
    private ServiceService mockedService;

    private Service entity;

    @BeforeEach
    void setup() {
        entity = new Service(ID, ServiceType.PSA);
    }

    @Test
    @DisplayName("should be able to get a service's data")
    void getServiceData() {
        when(repository.findById(ID)).thenReturn(Optional.of(entity));
        Optional<Service> entityOptional = mockedService.getById(ID);
        Service actualEntity = entityOptional.orElse(null);
        assert actualEntity != null;
        assertEquals(entity.getType().toString(), actualEntity.getType().toString());
    }

    @Test
    @DisplayName("should be able to save a service")
    void saveServiceData() {
        when(repository.save(entity)).thenReturn(entity);
        assertEquals(entity, mockedService.save(entity));
    }

    @Test
    @DisplayName("should be able to delete a service")
    void deleteServiceData() {
        doNothing().when(repository).deleteById(ID);
        mockedService.deleteById(ID);
        Mockito.verify(repository, times(1)).deleteById(ID);
    }
}
