package com.ninjaone.rmmrestapi.service;

import java.util.List;
import java.util.Optional;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.data.util.Streamable;
import org.springframework.stereotype.Service;

import com.ninjaone.rmmrestapi.database.DeviceRepository;
import com.ninjaone.rmmrestapi.model.Device;

@Service
public class DeviceService {
    private final DeviceRepository repository;

    public DeviceService(DeviceRepository repository) {
        this.repository = repository;
    }

    public Device save(Device entity) throws ConstraintViolationException {
        return repository.save(entity);
    }

    public List<Device> getAll() {
        return Streamable.of(repository.findAll()).toList();
    }

    public Optional<Device> getById(Integer id) {
        return repository.findById(id);
    }

    public void deleteById(Integer id) {
        repository.deleteById(id);
    }
}
