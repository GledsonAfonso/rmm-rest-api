package com.ninjaone.rmmrestapi.controller;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ninjaone.rmmrestapi.dto.ServiceRequestDto;
import com.ninjaone.rmmrestapi.model.Service;
import com.ninjaone.rmmrestapi.service.DeviceService;
import com.ninjaone.rmmrestapi.service.ServiceService;

@RestController
@RequestMapping("/service")
public class ServiceController {
    private final ServiceService service;
    private final DeviceService deviceService;

    public ServiceController(ServiceService service, DeviceService deviceService) {
        this.service = service;
        this.deviceService = deviceService;
    }

    @PostMapping
    public ResponseEntity<Service> post(@RequestBody ServiceRequestDto request) {
        var device = deviceService.getById(request.getDeviceId());

        if (device.isPresent()) {
            var entity = new Service(request.getType(), device.get());

            try {
                var savedEntity = service.save(entity);
                return ResponseEntity.status(HttpStatus.CREATED).body(savedEntity);
            } catch (DataIntegrityViolationException exception) {
                return ResponseEntity.badRequest().build();
            }
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Service> deleteServiceEntity(@PathVariable Integer id) {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
