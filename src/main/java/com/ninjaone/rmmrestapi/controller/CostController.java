package com.ninjaone.rmmrestapi.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ninjaone.rmmrestapi.model.Cost;
import com.ninjaone.rmmrestapi.service.CostService;

@RestController
@RequestMapping("/cost")
public class CostController {
    private final CostService service;

    public CostController(CostService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<Cost> getCost() {
        var cost = service.getCurrentMonthlyCost();
        return ResponseEntity.ok(cost);
    }
}
