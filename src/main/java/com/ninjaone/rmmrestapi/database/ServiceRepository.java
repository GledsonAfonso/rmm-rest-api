package com.ninjaone.rmmrestapi.database;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.ninjaone.rmmrestapi.model.Service;

@Repository
public interface ServiceRepository extends CrudRepository<Service, Integer> {}
