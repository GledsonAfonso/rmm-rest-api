package com.ninjaone.rmmrestapi.controller;

import java.util.List;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ninjaone.rmmrestapi.dto.DeviceRequestDto;
import com.ninjaone.rmmrestapi.model.Device;
import com.ninjaone.rmmrestapi.service.DeviceService;

@RestController
@RequestMapping("/device")
public class DeviceController {
    private final DeviceService service;

    public DeviceController(DeviceService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Device> post(@RequestBody DeviceRequestDto request) {
        var entity = new Device(request);

        try {
            var savedEntity = service.save(entity);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedEntity);
        } catch (DataIntegrityViolationException exception) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    @GetMapping
    public ResponseEntity<List<Device>> get() {
        var results = service.getAll();

        if (!results.isEmpty()) {
            return ResponseEntity.ok(results);
        }
        return ResponseEntity.notFound().build();

    }

    @GetMapping("/{id}")
    public ResponseEntity<Device> get(@PathVariable Integer id) {
        var result = service.getById(id);

        if (result.isPresent()) {
            var device = result.get();
            return ResponseEntity.ok(device);
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Device> update(@PathVariable Integer id, @RequestBody DeviceRequestDto request) {
        var deviceOptional = service.getById(id);

        if (deviceOptional.isPresent()) {
            var device = deviceOptional.get();
            device.setDtoInfo(request);

            try {
                var savedEntity = service.save(device);
                return ResponseEntity.accepted().body(savedEntity);
            } catch (DataIntegrityViolationException exception) {
                return ResponseEntity.badRequest().build();
            }
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Device> deleteServiceEntity(@PathVariable Integer id) {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
