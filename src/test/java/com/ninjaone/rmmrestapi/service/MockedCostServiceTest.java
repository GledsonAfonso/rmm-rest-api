package com.ninjaone.rmmrestapi.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.ninjaone.rmmrestapi.model.Device;
import com.ninjaone.rmmrestapi.model.DeviceType;
import com.ninjaone.rmmrestapi.model.Service;
import com.ninjaone.rmmrestapi.model.ServiceType;

@ExtendWith(MockitoExtension.class)
public class MockedCostServiceTest {
    @Mock
    private DeviceService deviceService;

    @InjectMocks
    private CostService costService;

    private List<Device> entities;

    @BeforeEach
    void setup() {
        entities = List.of(
            new Device(1, "device 1", DeviceType.WINDOWS, List.of(
                new Service(1, ServiceType.ANTIVIRUS),
                new Service(2, ServiceType.BACKUP),
                new Service(3, ServiceType.PSA),
                new Service(4, ServiceType.SCREEN_SHARE)
            )),
            new Device(2, "device 2", DeviceType.WINDOWS, List.of(
                new Service(5, ServiceType.ANTIVIRUS),
                new Service(6, ServiceType.BACKUP),
                new Service(7, ServiceType.SCREEN_SHARE)
            )),
            new Device(3, "device 3", DeviceType.MAC, List.of(
                new Service(8, ServiceType.ANTIVIRUS),
                new Service(9, ServiceType.BACKUP),
                new Service(10, ServiceType.PSA),
                new Service(11, ServiceType.SCREEN_SHARE)
            )),
            new Device(4, "device 4", DeviceType.MAC, List.of(
                new Service(12, ServiceType.ANTIVIRUS),
                new Service(13, ServiceType.BACKUP),
                new Service(14, ServiceType.SCREEN_SHARE)
            )),
            new Device(5, "device 5", DeviceType.MAC, List.of(
                new Service(15, ServiceType.ANTIVIRUS),
                new Service(16, ServiceType.BACKUP),
                new Service(17, ServiceType.PSA),
                new Service(18, ServiceType.SCREEN_SHARE)
            ))
        );
    }

    @Test
    @DisplayName("should be able to calculate the total monthly cost correctly")
    void getMonthlyCostTest() {
        when(deviceService.getAll()).thenReturn(entities);

        var totalMonthlyCost = costService.getCurrentMonthlyCost();
        assertEquals(BigDecimal.valueOf(20), totalMonthlyCost.getDevicesCost());
        assertEquals(BigDecimal.valueOf(31), totalMonthlyCost.getAntivirusCost());
        assertEquals(BigDecimal.valueOf(15), totalMonthlyCost.getBackupCost());
        assertEquals(BigDecimal.valueOf(6), totalMonthlyCost.getPsaCost());
        assertEquals(BigDecimal.valueOf(5), totalMonthlyCost.getScreenShare());
        assertEquals(BigDecimal.valueOf(77), totalMonthlyCost.getTotalCost());
    }

    @Test
    @DisplayName("should be able to calculate the total monthly cost for the given example in the exercise correctly")
    void getMonthlyCostForExampleGivenTest() {
        var customEntities = List.of(
            new Device(1, "device 1", DeviceType.WINDOWS, List.of(
                new Service(1, ServiceType.ANTIVIRUS),
                new Service(2, ServiceType.BACKUP),
                new Service(3, ServiceType.SCREEN_SHARE)
            )),
            new Device(2, "device 2", DeviceType.WINDOWS, List.of(
                new Service(1, ServiceType.ANTIVIRUS),
                new Service(2, ServiceType.BACKUP),
                new Service(3, ServiceType.SCREEN_SHARE)
            )),
            new Device(3, "device 3", DeviceType.MAC, List.of(
                new Service(1, ServiceType.ANTIVIRUS),
                new Service(2, ServiceType.BACKUP),
                new Service(3, ServiceType.SCREEN_SHARE)
            )),
            new Device(4, "device 4", DeviceType.MAC, List.of(
                new Service(1, ServiceType.ANTIVIRUS),
                new Service(2, ServiceType.BACKUP),
                new Service(3, ServiceType.SCREEN_SHARE)
            )),
            new Device(5, "device 5", DeviceType.MAC, List.of(
                new Service(1, ServiceType.ANTIVIRUS),
                new Service(2, ServiceType.BACKUP),
                new Service(3, ServiceType.SCREEN_SHARE)
            ))
        );
        when(deviceService.getAll()).thenReturn(customEntities);

        var totalMonthlyCost = costService.getCurrentMonthlyCost();
        assertEquals(BigDecimal.valueOf(20), totalMonthlyCost.getDevicesCost());
        assertEquals(BigDecimal.valueOf(31), totalMonthlyCost.getAntivirusCost());
        assertEquals(BigDecimal.valueOf(15), totalMonthlyCost.getBackupCost());
        assertEquals(BigDecimal.valueOf(0), totalMonthlyCost.getPsaCost());
        assertEquals(BigDecimal.valueOf(5), totalMonthlyCost.getScreenShare());
        assertEquals(BigDecimal.valueOf(71), totalMonthlyCost.getTotalCost());
    }

    @Test
    @DisplayName("should give a zeroed cost result when there's no devices and services registered")
    void getZeroCostCaseTest() {
        var customEntities = new ArrayList<Device>();
        when(deviceService.getAll()).thenReturn(customEntities);

        var totalMonthlyCost = costService.getCurrentMonthlyCost();
        assertEquals(BigDecimal.valueOf(0), totalMonthlyCost.getDevicesCost());
        assertEquals(BigDecimal.valueOf(0), totalMonthlyCost.getAntivirusCost());
        assertEquals(BigDecimal.valueOf(0), totalMonthlyCost.getBackupCost());
        assertEquals(BigDecimal.valueOf(0), totalMonthlyCost.getPsaCost());
        assertEquals(BigDecimal.valueOf(0), totalMonthlyCost.getScreenShare());
        assertEquals(BigDecimal.valueOf(0), totalMonthlyCost.getTotalCost());
    }
}
