package com.vivacon.controller;

import com.vivacon.entity.enum_type.SettingType;
import com.vivacon.service.SettingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserSettingController {

    private SettingService settingService;

    public UserSettingController() {

    }

    public ResponseEntity<Object> changeSetting(SettingType settingType, String value) {
        settingService.changeSetting(settingType, value);
        return ResponseEntity.ok(null);
    }
}
