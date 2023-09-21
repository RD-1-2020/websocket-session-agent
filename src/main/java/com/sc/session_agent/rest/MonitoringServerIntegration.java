package com.sc.session_agent.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.time.temporal.ChronoUnit;

import static java.net.http.HttpResponse.BodyHandlers.ofString;

@Service
public class MonitoringServerIntegration {
    private final static Logger logger = LoggerFactory.getLogger(MonitoringServerIntegration.class);

    private final static String CREATE_API_KEY_API_TEMPLATE = "session/api/key/create?mfcId=%d&windowId=%d";
    private final static String HEALTH_CHECK_API_TEMPLATE = "session/health/check?apiKey=%s";

    @Value("${remote.monitoring.server.url:}")
    private String monitoringServerUrl;

    public String createApiKey(Long mfcId, Long windowId) {
        logger.info("Try send request for create api key...");

        URI requestUri = URI.create(String.format(monitoringServerUrl + CREATE_API_KEY_API_TEMPLATE, mfcId, windowId));
        HttpRequest responseWithApiKey = HttpRequest
                .newBuilder()
                .uri(requestUri)
                .timeout(Duration.of(10, ChronoUnit.SECONDS))
                .build();

        try {
            HttpResponse<String> response = HttpClient.newBuilder().build().send(responseWithApiKey, ofString());

            logger.info("ApiKey successfully revived!");

            return response.body();
        } catch (Exception exception) {
            logger.error("In process get api key from server, something went wrong!", exception);
            return null;
        }
    }

    public Boolean healthCheck(String apiKey) throws Exception {
        logger.info("Try send request for health check...");

        URI requestUri = URI.create(String.format(monitoringServerUrl + HEALTH_CHECK_API_TEMPLATE, apiKey));
        HttpRequest responseWithApiKey = HttpRequest
                .newBuilder()
                .uri(requestUri)
                .timeout(Duration.of(10, ChronoUnit.SECONDS))
                .build();


        HttpResponse<String> response = HttpClient.newBuilder().build().send(responseWithApiKey, ofString());

        String stringBody = response.body();
        logger.info("Health check successfully received! Apikey valid state = {}", stringBody);

        return Boolean.parseBoolean(stringBody);
    }
}
