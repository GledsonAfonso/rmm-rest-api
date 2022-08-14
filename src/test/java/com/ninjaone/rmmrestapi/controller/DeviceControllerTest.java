package com.ninjaone.rmmrestapi.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;
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
import com.ninjaone.rmmrestapi.dto.DeviceRequestDto;
import com.ninjaone.rmmrestapi.model.Device;
import com.ninjaone.rmmrestapi.model.DeviceType;
import com.ninjaone.rmmrestapi.model.Service;
import com.ninjaone.rmmrestapi.service.DeviceService;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = { Application.class })
@WebMvcTest(DeviceController.class)
@AutoConfigureMockMvc
@AutoConfigureDataJpa
public class DeviceControllerTest {
    public static final Integer ID = 12345;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    private DeviceService service;

    private Device entity;

    @BeforeEach
    void setup() {
        entity = new Device(ID, "new device", DeviceType.WINDOWS, new ArrayList<Service>());
    }

    @Test
    @DisplayName("should be able to insert a device")
    void postServiceData() throws Exception {
        when(service.save(any())).thenReturn(entity);

        var request = new DeviceRequestDto();
        request.setName(entity.getName());
        request.setType(entity.getType());

        String requestString = objectMapper.writeValueAsString(request);
        String entityString = objectMapper.writeValueAsString(entity);
        mockMvc.perform(post("/device")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestString))
                .andExpect(status().isCreated())
                .andExpect(content().string(entityString));
    }

    @Test
    @DisplayName("should return an HTTP status code of 400 when trying to duplicate a device")
    void postDuplicateServiceData() throws Exception {
        when(service.save(any())).thenThrow(ConstraintViolationException.class);

        var request = new DeviceRequestDto();
        request.setName(entity.getName());
        request.setType(entity.getType());

        String requestString = objectMapper.writeValueAsString(request);
        mockMvc.perform(post("/device")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestString))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("should be able to get all devices")
    void getAllTest() throws Exception {
        var entities = List.of(entity);
        when(service.getAll()).thenReturn(entities);

        mockMvc.perform(get("/device"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().string(objectMapper.writeValueAsString(entities)));
    }

    @Test
    @DisplayName("should return an HTTP status of 404 if there's no results when trying to get all devices")
    void getAllEmptyTest() throws Exception {
        when(service.getAll()).thenReturn(new ArrayList<>());
        mockMvc.perform(get("/device")).andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("should be able to get a device by its id")
    void getByIdTest() throws Exception {
        when(service.getById(ID)).thenReturn(Optional.of(entity));

        mockMvc.perform(get("/device/" + ID))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().string(objectMapper.writeValueAsString(entity)));
    }

    @Test
    @DisplayName("should return an HTTP status of 404 if there's no results when trying to get a device by its id")
    void getByIdEmptyTest() throws Exception {
        when(service.getById(ID)).thenReturn(Optional.empty());
        mockMvc.perform(get("/device/" + ID)).andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("should be able to update a device by its id")
    void putTest() throws Exception {
        when(service.save(any())).thenReturn(entity);
        when(service.getById(ID)).thenReturn(Optional.of(entity));

        var request = new DeviceRequestDto();
        request.setName(entity.getName());
        request.setType(entity.getType());

        String requestString = objectMapper.writeValueAsString(request);
        String entityString = objectMapper.writeValueAsString(entity);
        mockMvc.perform(put("/device/" + ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestString))
                .andExpect(status().isAccepted())
                .andExpect(content().string(entityString));
    }

    @Test
    @DisplayName("should return an HTTP status of 400 when trying to duplicate devices through the put method")
    void putDuplicateTest() throws Exception {
        when(service.getById(ID)).thenReturn(Optional.of(entity));
        when(service.save(any())).thenThrow(ConstraintViolationException.class);

        var request = new DeviceRequestDto();
        request.setName(entity.getName());
        request.setType(entity.getType());

        String requestString = objectMapper.writeValueAsString(request);
        mockMvc.perform(put("/device/" + ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestString))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("should return an HTTP status of 404 if the device you're trying to update does not exist")
    void putNonExistentTest() throws Exception {
        when(service.getById(ID)).thenReturn(Optional.empty());

        var request = new DeviceRequestDto();
        request.setName(entity.getName());
        request.setType(entity.getType());

        String requestString = objectMapper.writeValueAsString(request);
        mockMvc.perform(put("/device/" + ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestString))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("should be able to delete a device by its id")
    void deleteServiceData() throws Exception {
        doNothing().when(service).deleteById(ID);

        mockMvc.perform(delete("/device/" + ID))
                .andExpect(status().isNoContent());
    }
}
