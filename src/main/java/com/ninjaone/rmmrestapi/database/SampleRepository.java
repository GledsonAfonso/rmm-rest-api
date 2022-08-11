package com.ninjaone.rmmrestapi.database;

import com.ninjaone.rmmrestapi.model.Sample;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SampleRepository extends CrudRepository<Sample, String> {}
