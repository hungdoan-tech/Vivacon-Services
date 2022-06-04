package com.vivacon.service;

import com.vivacon.entity.enum_type.SettingType;

public interface SettingService {

    void changeSetting(SettingType settingType, String value);

    boolean evaluateSetting(SettingType settingType, String settingValue, String compareValue);
}
