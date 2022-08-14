package com.ninjaone.rmmrestapi.model;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class ServiceTypeConverter implements AttributeConverter<ServiceType, String> {
  @Override
  public String convertToDatabaseColumn(ServiceType type) {
      if (type == null) {
          return null;
      }
      return type.getValue();
  }

  @Override
  public ServiceType convertToEntityAttribute(String value) {
      if (value == null) {
          return null;
      }

      return ServiceType.of(value);
  }
}
