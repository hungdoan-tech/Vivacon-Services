package com.vivacon.service;

import com.vivacon.entity.Account;

import javax.servlet.http.HttpServletRequest;

public interface DeviceService {
    void verifyDevice(Account user, HttpServletRequest request);
}
