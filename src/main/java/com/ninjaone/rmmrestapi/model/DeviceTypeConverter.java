package com.ninjaone.rmmrestapi.model;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class DeviceTypeConverter implements AttributeConverter<DeviceType, String> {
  @Override
  public String convertToDatabaseColumn(DeviceType type) {
      if (type == null) {
          return null;
      }
      return type.getValue();
  }

  @Override
  public DeviceType convertToEntityAttribute(String value) {
      if (value == null) {
          return null;
      }

      return DeviceType.of(value);
  }
}
