package com.vivacon.entity.enum_type;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public enum SettingType {

    EMAIL_ON_REPORTING_RESULT(Boolean.class, String.valueOf(true)),

    EMAIL_ON_MISSED_ACTIVITIES(Boolean.class, String.valueOf(true)),

    PUSH_NOTIFICATION_ON_COMMENT(Boolean.class, String.valueOf(true)),

    PUSH_NOTIFICATION_ON_LIKE(Boolean.class, String.valueOf(true)),

    PUSH_NOTIFICATION_ON_FOLLOWING(Boolean.class, String.valueOf(true));

    Class valueType;

    String defaultValue;

    SettingType(Class valueType, String defaultValue) {
        this.valueType = valueType;
        this.defaultValue = defaultValue;
    }

    public Class getValueType() {
        return valueType;
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    public Object deserialize(String currentValue) {
        try {
            return new ObjectMapper().readValue(currentValue, valueType);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
