package com.vivacon.service.impl;

import com.vivacon.dto.ChangeSettingRequest;
import com.vivacon.dto.response.SettingResponse;
import com.vivacon.entity.enum_type.SettingType;
import com.vivacon.exception.RecordNotFoundException;
import com.vivacon.repository.SettingRepository;
import com.vivacon.service.AccountService;
import com.vivacon.service.SettingService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SettingServiceImpl implements SettingService {

    private SettingRepository settingRepository;

    private AccountService accountService;

    public SettingServiceImpl(SettingRepository settingRepository,
                              AccountService accountService) {
        this.settingRepository = settingRepository;
        this.accountService = accountService;
    }

    @Override
    public List<SettingResponse> getSettings() {
        Long accountId = accountService.getCurrentAccount().getId();
        return settingRepository.findAllByAccountId(accountId)
                .stream().map(setting -> new SettingResponse(setting))
                .collect(Collectors.toList());
    }

    @Override
    public boolean changeSetting(ChangeSettingRequest changeSettingRequest) {
        Long accountId = accountService.getCurrentAccount().getId();
        SettingType type = changeSettingRequest.getSettingType();
        String value = type.serialize(changeSettingRequest.getValue());
        return settingRepository.updateValueBySettingTypeAndAccountId(accountId, type,
                changeSettingRequest.getValue()) > 0;
    }

    @Override
    public Object evaluateSetting(SettingType settingType) {
        Long accountId = accountService.getCurrentAccount().getId();
        String currentSettingValue = settingRepository
                .findValueByAccountIdAndSettingType(accountId, settingType)
                .orElseThrow(RecordNotFoundException::new);
        return settingType.deserialize(currentSettingValue);
    }
}
