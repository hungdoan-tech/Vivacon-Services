package com.vivacon.service.impl;

import com.vivacon.entity.enum_type.SettingType;
import com.vivacon.exception.RecordNotFoundException;
import com.vivacon.repository.SettingRepository;
import com.vivacon.service.AccountService;
import com.vivacon.service.SettingService;

public class SettingServiceImpl implements SettingService {

    private SettingRepository settingRepository;

    private AccountService accountService;

    public SettingServiceImpl(SettingRepository settingRepository,
                              AccountService accountService) {
        this.settingRepository = settingRepository;
        this.accountService = accountService;
    }

    @Override
    public void changeSetting(SettingType settingType, String value) {

    }

    @Override
    public boolean evaluateSetting(SettingType settingType, String settingValue, String compareValue) {
        Long accountId = accountService.getCurrentAccount().getId();
        String currentSetting = settingRepository
                .findValueByAccountIdAndSettingType(accountId, settingType.toString())
                .orElseThrow(RecordNotFoundException::new);
        return false;
    }
}
