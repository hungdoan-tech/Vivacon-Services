package com.vivacon.service.impl;

import com.vivacon.dto.ChangeSettingRequest;
import com.vivacon.dto.response.SettingResponse;
import com.vivacon.entity.enum_type.SettingType;
import com.vivacon.exception.RecordNotFoundException;
import com.vivacon.repository.SettingRepository;
import com.vivacon.service.AccountService;
import com.vivacon.service.SettingService;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.NonTransientDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
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
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {DataIntegrityViolationException.class, NonTransientDataAccessException.class, SQLException.class, Exception.class})
    public boolean changeSetting(ChangeSettingRequest changeSettingRequest) {
        Long accountId = accountService.getCurrentAccount().getId();
        SettingType type = changeSettingRequest.getSettingType();
        if (type.isValidValue(changeSettingRequest.getValue())) {
            String value = type.serialize(changeSettingRequest.getValue());
            return settingRepository.updateValueBySettingTypeAndAccountId(accountId, type, value) > 0;
        } else {
            throw new RuntimeException("Providing invalid value to change setting");
        }
    }

    @Override
    public Object evaluateSetting(long accountId, SettingType settingType) {
        String currentSettingValue = settingRepository
                .findValueByAccountIdAndSettingType(accountId, settingType)
                .orElseThrow(RecordNotFoundException::new);
        return settingType.deserialize(currentSettingValue);
    }
}