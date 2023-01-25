package com.tech.ash.abuseipdb.controller;

import com.tech.ash.abuseipdb.model.ReportIPRequest;
import com.tech.ash.abuseipdb.service.AbuseIPDBService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class AbuseIPDBController {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final String IP = "ip";
    private static final String IP_ADDRESS = "ipAddress";
    private final AbuseIPDBService abuseIPDBService;

    public AbuseIPDBController(@Autowired AbuseIPDBService abuseIPDBService) {
        this.abuseIPDBService = abuseIPDBService;
    }

    @GetMapping("/checkip/{ipAddress}")
    public ResponseEntity<String> checkIPAddress(@PathVariable String ipAddress) {
        LOGGER.info("Checking for IP Address {}.", ipAddress);
        return abuseIPDBService.checkIPAddress(ipAddress);
    }

    @PostMapping("/reportip")
    public ResponseEntity<String> reportIPAddress(@RequestParam String ip,
                                                  @RequestParam String categories,
                                                  @RequestParam String comments) {
        LOGGER.info("Reporting IP Address {}.", ip);
        ReportIPRequest reportIPRequest = new ReportIPRequest();
        reportIPRequest.setIp(ip);
        reportIPRequest.setCategories(categories);
        reportIPRequest.setComments(comments);

        return abuseIPDBService.reportIPAddress(reportIPRequest);
    }
}
