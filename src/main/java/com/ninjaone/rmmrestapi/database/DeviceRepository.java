package com.ninjaone.rmmrestapi.database;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.ninjaone.rmmrestapi.model.Device;

@Repository
public interface DeviceRepository extends CrudRepository<Device, Integer> {}
