package com.tech.ash.abuseipdb.service;

import com.tech.ash.abuseipdb.config.AbuseIPDBConfig;
import com.tech.ash.abuseipdb.model.ReportIPRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.URISyntaxException;

@Service
public class AbuseIPDBService {
    public static final Logger LOGGER = LogManager.getLogger(AbuseIPDBService.class);
    private final SpringRestTemplateService springRestTemplateService;
    private static final String IP = "ip";
    private static final String CATEGORIES = "categories";
    private static final String COMMENTS = "comments";
    private static final String KEY = "Key";
    private static final String ACCEPT = "Accept";
    private static final String APPLICATION_JSON = "application/json";
    private static final String EQUALS = "=";
    private static final String AMPERSAND = "&";
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

            RequestEntity requestEntity = new RequestEntity<>(getHttpHeaders(), HttpMethod.GET, uri);
            ResponseEntity responseEntity = springRestTemplateService.checkIPAddress(requestEntity);
            LOGGER.info(responseEntity);
            return responseEntity;
        } catch (URISyntaxException e) {
            LOGGER.error("URISyntaxException checking IP Address {}.", ipAddress);
            return ResponseEntity.internalServerError().build();
        }
    }

    public ResponseEntity reportIPAddress(ReportIPRequest reportIPRequest) {
        LOGGER.info("Reporting IP Address {}.", reportIPRequest);
        URI uri = null;

        try {
            uri = new URI(abuseIPDBConfig.getReportIpAddressUrl());

            StringBuilder body = new StringBuilder();
            body.append(IP).append(EQUALS).append(reportIPRequest.getIp()).append(AMPERSAND)
                    .append(CATEGORIES).append(EQUALS).append(reportIPRequest.getCategories())
                    .append(AMPERSAND).append(COMMENTS).append(EQUALS).append(reportIPRequest.getComments());

            RequestEntity requestEntity = new RequestEntity<>(body.toString(), postHttpHeaders(), HttpMethod.POST, uri);
            ResponseEntity responseEntity = springRestTemplateService.reportIPAddress(requestEntity);
            LOGGER.info(responseEntity);
            return responseEntity;
        } catch (URISyntaxException e) {
            LOGGER.error("URISyntaxException reporting IP Address {}.", reportIPRequest.getIp());
            return ResponseEntity.internalServerError().build();
        }
    }

    private HttpHeaders getHttpHeaders() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(KEY, abuseIPDBConfig.getAbuseIpDbKey());
        httpHeaders.add(ACCEPT, APPLICATION_JSON);
        return httpHeaders;
    }

    private HttpHeaders postHttpHeaders() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        httpHeaders.add(KEY, abuseIPDBConfig.getAbuseIpDbKey());
        httpHeaders.add(ACCEPT, APPLICATION_JSON);
        return httpHeaders;
    }
}
