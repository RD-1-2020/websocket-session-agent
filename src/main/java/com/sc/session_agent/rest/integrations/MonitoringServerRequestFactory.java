package com.sc.session_agent.rest.integrations;

import com.sc.session_agent.utils.UriUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.net.http.HttpRequest;
import java.time.Duration;
import java.time.temporal.ChronoUnit;

@Service
public class MonitoringServerRequestFactory {
    private final static String CREATE_API_KEY_API_TEMPLATE = "session/api/key/create?mfcId=%d&windowId=%d";
    private final static String HEALTH_CHECK_API_TEMPLATE = "session/health/check?apiKey=%s";
    private static final Duration TIMEOUT = Duration.of(10_000L, ChronoUnit.SECONDS);

    @Value("${remote.monitoring.server.url:}")
    private String monitoringServerUrl;

    @Autowired
    private UriUtils uriUtils;

    public HttpRequest createApiKeyGenerateRequest(Long mfcId, Long windowId) {
        return HttpRequest
                .newBuilder()
                .method("GET", HttpRequest.BodyPublishers.noBody())
                .uri(uriUtils.create(monitoringServerUrl + CREATE_API_KEY_API_TEMPLATE, mfcId, windowId))
                .timeout(TIMEOUT)
                .build();
    }

    public HttpRequest createHealthCheckRequest(String apiKey) {
        return HttpRequest
                .newBuilder()
                .method("GET", HttpRequest.BodyPublishers.noBody())
                .uri(uriUtils.create(monitoringServerUrl + HEALTH_CHECK_API_TEMPLATE, apiKey))
                .timeout(TIMEOUT)
                .build();
    }
}
