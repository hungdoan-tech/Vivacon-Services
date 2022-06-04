package com.vivacon.controller;

import com.vivacon.dto.response.SettingResponse;
import com.vivacon.entity.enum_type.SettingType;
import com.vivacon.service.SettingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserSettingController {

    private SettingService settingService;

    public UserSettingController(SettingService settingService) {

    }

    public ResponseEntity<Object> changeSetting(SettingType settingType, String value) {
        settingService.changeSetting(settingType, value);
        return ResponseEntity.ok(null);
    }

    public List<SettingResponse> getSettings() {
        return settingService.getSettings();
    }
}
