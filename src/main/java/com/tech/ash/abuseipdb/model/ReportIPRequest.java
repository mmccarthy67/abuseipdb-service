package com.tech.ash.abuseipdb.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReportIPRequest {
    private String ip;
    private String categories;
    private String comments;
}
