package com.tech.ash.abuseipdb.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Service
public class SpringRestTemplateService {
    private static final Logger LOGGER = LoggerFactory.getLogger(SpringRestTemplateService.class);
    private static final int MAX_ATTEMPTS = 5;
    private static final long DELAY = 5000;

    @Retryable(value = {RestClientException.class}, maxAttempts = MAX_ATTEMPTS, backoff = @Backoff(delay = DELAY))
    public ResponseEntity checkIPAddress(RequestEntity requestEntity) {
        return getRestTemplate().exchange(requestEntity, new ParameterizedTypeReference<String>() {});
    }

    @Retryable(value = {RestClientException.class}, maxAttempts = MAX_ATTEMPTS, backoff = @Backoff(delay = DELAY))
    public ResponseEntity reportIPAddress(RequestEntity requestEntity) {
        return getRestTemplate().exchange(requestEntity, new ParameterizedTypeReference<String>() {});
    }

    @Recover
    public String recover(RestClientException e) {
        LOGGER.error("Recovering from Rest Client Exception.", e);
        throw e;
    }

    private RestTemplate getRestTemplate() {
        return new RestTemplate();
    }
}
