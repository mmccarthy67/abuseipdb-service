package com.tech.ash.abuseipdb.controller;

import com.tech.ash.abuseipdb.service.AbuseIPDBService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AbuseIPDBController {
    private static final Logger LOGGER = LogManager.getLogger();
    private final AbuseIPDBService abuseIPDBService;

    public AbuseIPDBController(@Autowired AbuseIPDBService abuseIPDBService) {
        this.abuseIPDBService = abuseIPDBService;
    }

    @GetMapping("/checkip/{ipAddress}")
    public ResponseEntity<String> checkIPAddress(@PathVariable String ipAddress) {
        LOGGER.info("Checking for IP Address {}.", ipAddress);
        return abuseIPDBService.checkIPAddress(ipAddress);
    }
}
