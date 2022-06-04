package com.vivacon.service;

import com.vivacon.dto.response.SettingResponse;
import com.vivacon.entity.enum_type.SettingType;

import java.util.List;

public interface SettingService {

    void changeSetting(SettingType settingType, String value);

    boolean evaluateSetting(SettingType settingType, String settingValue, String compareValue);

    List<SettingResponse> getSettings();
}
