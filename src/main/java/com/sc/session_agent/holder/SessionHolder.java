package com.sc.session_agent.holder;

import com.sc.session_agent.model.session.server.HealthCheckServerData;
import com.sc.session_agent.rest.integrations.MonitoringServerIntegration;
import com.sc.session_agent.service.SessionFileService;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import static org.apache.logging.log4j.util.Strings.EMPTY;

@Service
public class SessionHolder {
    private final static Logger logger = LoggerFactory.getLogger(SessionHolder.class);

    private String apiKey;

    @Autowired
    private SessionFileService sessionFileService;

    @Autowired
    private MonitoringServerIntegration monitoringServerIntegration;

    @PostConstruct
    public void init() {
        logger.info("Try get api key from file on application start...");

        this.apiKey = sessionFileService.getApiKeyFromSessionFile();

        logger.info("Api key from session file successfully got!");
    }

    public void updateActiveSessionKey(Long mfcId, Long windowId) {
        logger.info("Try update api key...");
        if (StringUtils.hasText(apiKey)) {
            logger.warn("When try update key, old key is not blank!");
        }

        String apiKey = monitoringServerIntegration.createApiKey(mfcId, windowId);
        this.apiKey = sessionFileService.rewriteApiKeyToFile(apiKey);

        logger.info("Api key successfully updated!");
    }

    public HealthCheckServerData healthCheck() {
        logger.info("Start try validate api key...");

        HealthCheckServerData serverData = new HealthCheckServerData();
        try {
            serverData.setValid(monitoringServerIntegration.healthCheck(this.apiKey));

            if (!serverData.isValid()) {
                sessionFileService.rewriteApiKeyToFile(EMPTY);
                apiKey = null;
            }
        } catch (Exception exception) {
            serverData.setServerAccessible(false);
        }

        return serverData;
    }

    public String getApiKey() {
        return apiKey;
    }
}
