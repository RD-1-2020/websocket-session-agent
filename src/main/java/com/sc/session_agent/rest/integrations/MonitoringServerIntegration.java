package com.sc.session_agent.rest.integrations;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.http.HttpClient;
import java.net.http.HttpResponse;

import static java.net.http.HttpResponse.BodyHandlers.ofString;

@Service
public class MonitoringServerIntegration {
    private final static Logger logger = LoggerFactory.getLogger(MonitoringServerIntegration.class);
    
    @Autowired
    private MonitoringServerRequestFactory monitoringServerRequestFactory;

    public String createApiKey(Long mfcId, Long windowId) {
        logger.info("Try send request for create api key...");

        try {
            HttpResponse<String> response = HttpClient
                    .newBuilder()
                    .build()
                    .send(monitoringServerRequestFactory.createApiKeyGenerateRequest(mfcId, windowId), ofString());

            logger.info("ApiKey successfully revived!");

            return response.body();
        } catch (Exception exception) {
            logger.error("In process get api key from server, something went wrong!", exception);
            return null;
        }
    }

    public Boolean healthCheck(String apiKey) throws Exception {
        logger.info("Try send request for health check...");

        HttpResponse<String> response = HttpClient
                .newBuilder()
                .build()
                .send(monitoringServerRequestFactory.createHealthCheckRequest(apiKey), ofString());

        String stringBody = response.body();
        logger.info("Health check successfully received! Apikey valid state = {}", stringBody);

        return Boolean.parseBoolean(stringBody);
    }
}
