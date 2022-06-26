package com.vivacon.service;

import com.maxmind.geoip2.exception.GeoIp2Exception;
import com.vivacon.entity.Account;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public interface DeviceService {
    void verifyDevice(Account user, HttpServletRequest request) throws IOException, GeoIp2Exception;
}
