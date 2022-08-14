package com.ninjaone.rmmrestapi.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.Optional;

import org.hibernate.exception.ConstraintViolationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ninjaone.rmmrestapi.Application;
import com.ninjaone.rmmrestapi.dto.ServiceRequestDto;
import com.ninjaone.rmmrestapi.model.Device;
import com.ninjaone.rmmrestapi.model.DeviceType;
import com.ninjaone.rmmrestapi.model.Service;
import com.ninjaone.rmmrestapi.model.ServiceType;
import com.ninjaone.rmmrestapi.service.DeviceService;
import com.ninjaone.rmmrestapi.service.ServiceService;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = { Application.class })
@WebMvcTest(ServiceController.class)
@AutoConfigureMockMvc
@AutoConfigureDataJpa
public class ServiceControllerTest {
    public static final Integer ID = 12345;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    private ServiceService serviceService;

    @MockBean
    private DeviceService deviceService;

    private Service serviceEntity;
    private Device deviceEntity;

    @BeforeEach
    void setup() {
        deviceEntity = new Device(ID, "new service", DeviceType.WINDOWS, new ArrayList<>());
        serviceEntity = new Service(ServiceType.ANTIVIRUS, deviceEntity);
    }

    @Test
    @DisplayName("should be able to insert a service")
    void postServiceData() throws Exception {
        when(deviceService.getById(ID)).thenReturn(Optional.of(deviceEntity));
        when(serviceService.save(any())).thenReturn(serviceEntity);

        var request = new ServiceRequestDto();
        request.setType(serviceEntity.getType());
        request.setDeviceId(serviceEntity.getDevice().getId());

        String requestString = objectMapper.writeValueAsString(request);
        String entityString = objectMapper.writeValueAsString(serviceEntity);
        mockMvc.perform(post("/service")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestString))
                .andExpect(status().isCreated())
                .andExpect(content().string(entityString));
    }

    @Test
    @DisplayName("should return an HTTP status code of 400 when trying to duplicate a service")
    void postDuplicateServiceData() throws Exception {
        when(deviceService.getById(ID)).thenReturn(Optional.of(deviceEntity));
        when(serviceService.save(any())).thenThrow(ConstraintViolationException.class);

        var request = new ServiceRequestDto();
        request.setType(serviceEntity.getType());
        request.setDeviceId(serviceEntity.getDevice().getId());

        String requestString = objectMapper.writeValueAsString(request);
        mockMvc.perform(post("/service")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestString))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("should be able to delete a service by its id")
    void deleteServiceData() throws Exception {
        doNothing().when(serviceService).deleteById(ID);

        mockMvc.perform(delete("/service/" + ID))
                .andExpect(status().isNoContent());
    }
}
