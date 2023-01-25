package com.tech.ash.abuseipdb.service;

import com.tech.ash.abuseipdb.config.AbuseIPDBConfig;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.net.URI;
import java.net.URISyntaxException;

@Service
public class AbuseIPDBService {
    public static final Logger LOGGER = LogManager.getLogger(AbuseIPDBService.class);
    private final SpringRestTemplateService springRestTemplateService;
    private final AbuseIPDBConfig abuseIPDBConfig;

    public AbuseIPDBService(@Autowired SpringRestTemplateService springRestTemplateService,
                            @Autowired AbuseIPDBConfig abuseIPDBConfig) {
        this.springRestTemplateService = springRestTemplateService;
        this.abuseIPDBConfig = abuseIPDBConfig;
    }

    public ResponseEntity checkIPAddress(String ipAddress) {
        LOGGER.info("Checking IP Address {}.", ipAddress);
        URI uri = null;

        try {
            uri = new URI(abuseIPDBConfig.getCheckIpAddressUrl()
                    +"?maxAgeInDays="
                    +abuseIPDBConfig.getMaxAgeInDays()
                    +"&ipAddress="
                    +ipAddress);

            MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
            headers.add("Key", abuseIPDBConfig.getAbuseIpDbKey());
            headers.add("Accept", "application/json");

            RequestEntity requestEntity = new RequestEntity<>(headers, HttpMethod.GET, uri);

            return springRestTemplateService.checkIPAddress(requestEntity);
        } catch (URISyntaxException e) {
            LOGGER.error("URISyntaxException checking IP Address {}.", ipAddress);
            return ResponseEntity.internalServerError().build();
        }
    }
}
