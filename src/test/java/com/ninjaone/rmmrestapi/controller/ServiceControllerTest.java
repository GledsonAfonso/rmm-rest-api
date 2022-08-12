package com.ninjaone.rmmrestapi.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
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
import com.ninjaone.rmmrestapi.model.Service;
import com.ninjaone.rmmrestapi.model.ServiceType;
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
    private ServiceService service;

    private Service entity;

    @BeforeEach
    void setup() {
        entity = new Service(ID, ServiceType.PSA);
    }

    @Test
    void postServiceData() throws Exception {
        when(service.save(any())).thenReturn(entity);

        String entityString = objectMapper.writeValueAsString(entity);
        mockMvc.perform(post("/service")
                .contentType(MediaType.APPLICATION_JSON)
                .content(entityString))
                .andExpect(status().isCreated())
                .andExpect(content().string(entityString));
    }

    @Test
    void deleteServiceData() throws Exception {
        doNothing().when(service).deleteById(ID);

        mockMvc.perform(delete("/service/" + ID))
                .andExpect(status().isNoContent());
    }
}
