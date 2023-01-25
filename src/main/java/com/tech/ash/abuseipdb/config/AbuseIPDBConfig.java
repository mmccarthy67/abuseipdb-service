package com.tech.ash.abuseipdb.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
//@PropertySource("application.properties")
@Data
public class AbuseIPDBConfig {
    private final String abuseIpDbKey;
    private final String checkIpAddressUrl;
    private final String reportIpAddressUrl;
    private final String reportBulkIpAddressUrl;
    private final String maxAgeInDays;

    public AbuseIPDBConfig(@Value("${abuse.ip.db.key}") String abuseIpDbKey,
                           @Value("${abuse.ip.db.check.ip.url}") String checkIpAddressUrl,
                           @Value("${abuse.ip.db.report.ip.url}") String reportIpAddressUrl,
                           @Value("${abuse.ip.db.report.bulk.ip.url}") String reportBulkIpAddressUrl,
                           @Value("${abuse.ip.db.max.age.in.days}") String maxAgeInDays) {
        this.abuseIpDbKey = abuseIpDbKey;
        this.checkIpAddressUrl = checkIpAddressUrl;
        this.reportIpAddressUrl = reportIpAddressUrl;
        this.reportBulkIpAddressUrl = reportBulkIpAddressUrl;
        this.maxAgeInDays = maxAgeInDays;
    }
}
