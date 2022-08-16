package com.ninjaone.rmmrestapi.service;

import java.util.List;
import java.util.Optional;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.util.Streamable;

import com.ninjaone.rmmrestapi.database.ServiceRepository;
import com.ninjaone.rmmrestapi.model.Service;

@org.springframework.stereotype.Service
public class ServiceService {
    private final ServiceRepository repository;

    public ServiceService(ServiceRepository repository) {
        this.repository = repository;
    }

    public Service save(Service entity) throws DataIntegrityViolationException {
        return repository.save(entity);
    }

    public List<Service> getAll() {
        return Streamable.of(repository.findAll()).toList();
    }

    public Optional<Service> getById(Integer id) {
        return repository.findById(id);
    }

    public void deleteById(Integer id) {
        try {
            repository.deleteById(id);
        } catch (EmptyResultDataAccessException exception) {}
    }
}
