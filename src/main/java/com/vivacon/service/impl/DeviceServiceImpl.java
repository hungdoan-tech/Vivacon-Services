package com.vivacon.service.impl;

import com.maxmind.geoip2.DatabaseReader;
import com.maxmind.geoip2.exception.GeoIp2Exception;
import com.maxmind.geoip2.model.CityResponse;
import com.vivacon.entity.Account;
import com.vivacon.entity.DeviceMetadata;
import com.vivacon.repository.DeviceMetadataRepository;
import com.vivacon.service.DeviceService;
import org.springframework.stereotype.Service;
import ua_parser.Client;
import ua_parser.Parser;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.net.InetAddress;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;

@Service
public class DeviceServiceImpl implements DeviceService {
    private DeviceMetadataRepository deviceMetadataRepository;
    private DatabaseReader databaseReader;
    private Parser parser;

    public DeviceServiceImpl(DeviceMetadataRepository deviceMetadataRepository,
                             DatabaseReader databaseReader,
                             Parser parser) {
        this.deviceMetadataRepository = deviceMetadataRepository;
        this.databaseReader = databaseReader;
        this.parser = parser;
    }

    @Override
    public void verifyDevice(Account account, HttpServletRequest request) {

        String ip = extractIp(request);
        String location = getLocation(ip);
        String device = getDevice(request.getHeader("account-agent"));
        Optional<DeviceMetadata> existingDevice = deviceMetadataRepository.find(account.getId(), location, device);

        if (existingDevice.isEmpty()) {
            DeviceMetadata deviceMetadata = new DeviceMetadata();
            deviceMetadata.setAccount(account);
            deviceMetadata.setLocation(location);
            deviceMetadata.setDevice(device);
            deviceMetadata.setLastLoggedIn(LocalDateTime.now());
            deviceMetadataRepository.save(deviceMetadata);
            unknownDeviceNotification(device, location, ip, account.getEmail());
        } else {
            DeviceMetadata deviceMetadata = existingDevice.get();
            deviceMetadata.setLastLoggedIn(LocalDateTime.now());
            deviceMetadataRepository.save(deviceMetadata);
        }
    }

    private String extractIp(HttpServletRequest request) {
        String clientIp;
        String clientXForwardedForIp = request.getHeader("x-forwarded-for");
        if (clientXForwardedForIp != null) {
            clientIp = parseXForwardedHeader(clientXForwardedForIp);
        } else {
            clientIp = request.getRemoteAddr();
        }
        return clientIp;
    }

    private String parseXForwardedHeader(String header) {
        return header.split(" *, *")[0];
    }

    private String getDevice(String userAgent) {
        String deviceDetails = "UNKNOWN";
        Client client = parser.parse(userAgent);
        if (Objects.nonNull(client)) {
            deviceDetails = client.userAgent.family + " " + client.userAgent.major + "." + client.userAgent.minor +
                    " - " + client.os.family + " " + client.os.major + "." + client.os.minor;
        }
        return deviceDetails;
    }

    private String getLocation(String ip) {
        String location = "UNKNOWN";
        try {
            InetAddress ipAddress = InetAddress.getByName(ip);
            CityResponse cityResponse = databaseReader.city(ipAddress);
            Double latitude = cityResponse.getLocation().getLatitude();
            Double longitude = cityResponse.getLocation().getLongitude();
            if (Objects.nonNull(cityResponse) && Objects.nonNull(cityResponse.getCity()) && cityResponse.getCity().getName() != null) {

                Integer cityGeoNameId = cityResponse.getCountry().getGeoNameId();
                Integer nationGeoNameId = cityResponse.getCity().getGeoNameId();
                location = cityResponse.getCountry().getName() + " - " + cityResponse.getCity().getName();
            }
        } catch (IOException | GeoIp2Exception e) {
        } finally {
            return location;
        }
    }

    private void unknownDeviceNotification(String deviceDetails, String location, String ip, String email) {
        final String subject = "New Login Notification";
        String text = deviceDetails + location + ip;
    }
}
