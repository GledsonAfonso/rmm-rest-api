package com.ninjaone.rmmrestapi.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Sample {
    @Id
    private String id;

    @Column(name = "`value`")
    private String value;

    public Sample(){}
    public Sample(String id, String value) {
        this.id = id;
        this.value = value;
    }

    public String getId() {
        return id;
    }

    public String getValue() {
        return value;
    }
}
