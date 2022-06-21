package com.vivacon.controller;

import com.vivacon.dto.ChangeSettingRequest;
import com.vivacon.dto.response.SettingResponse;
import com.vivacon.service.SettingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

import static com.vivacon.common.constant.Constants.API_V1;

@RestController
@RequestMapping(API_V1 + "/setting")
public class UserSettingController {

    private SettingService settingService;

    public UserSettingController(SettingService settingService) {
        this.settingService = settingService;
    }

    @PostMapping
    public ResponseEntity<Object> changeSetting(@Valid @RequestBody ChangeSettingRequest changeSettingRequest) {
        if (settingService.changeSetting(changeSettingRequest)) {
            return ResponseEntity.ok(null);
        }
        return ResponseEntity.badRequest().body(null);
    }

    @GetMapping
    public List<SettingResponse> getSettings() {
        return settingService.getSettings();
    }


}
