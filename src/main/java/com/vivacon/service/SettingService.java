package com.vivacon.service;

import com.vivacon.dto.ChangeSettingRequest;
import com.vivacon.dto.response.SettingResponse;
import com.vivacon.entity.enum_type.SettingType;

import java.util.List;

public interface SettingService {

    Object evaluateSetting(SettingType settingType);

    List<SettingResponse> getSettings();

    boolean changeSetting(ChangeSettingRequest changeSettingRequest);
}
