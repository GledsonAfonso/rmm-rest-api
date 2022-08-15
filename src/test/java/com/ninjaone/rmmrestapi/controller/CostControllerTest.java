package com.ninjaone.rmmrestapi.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
import com.ninjaone.rmmrestapi.model.Cost;
import com.ninjaone.rmmrestapi.service.CostService;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = { Application.class })
@WebMvcTest(CostController.class)
@AutoConfigureMockMvc
@AutoConfigureDataJpa
public class CostControllerTest {
    public static final Integer ID = 12345;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    private CostService service;

    private Cost entity;

    @BeforeEach
    void setup() {
        entity = new Cost();
    }

    @Test
    @DisplayName("should be able to get the monthly cost")
    void getCostTest() throws Exception {
        when(service.getCurrentMonthlyCost()).thenReturn(entity);

        mockMvc.perform(get("/cost"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().string(objectMapper.writeValueAsString(entity)));
    }
}
