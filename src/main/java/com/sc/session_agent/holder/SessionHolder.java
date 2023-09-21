package com.sc.session_agent.holder;

import com.sc.session_agent.service.SessionFileService;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class SessionHolder {
    private final static Logger logger = LoggerFactory.getLogger(SessionHolder.class);

    private String apiKey;

    @Autowired
    private SessionFileService sessionFileService;

    @PostConstruct
    public void init() {
        logger.info("Try get api key from file on application start...");

        this.apiKey = sessionFileService.getApiKeyFromSessionFile();

        logger.info("Api key from session file successfully got!");
    }

    public void updateActiveSessionKey(String apiKey) {
        logger.info("Try update api key...");
        if (StringUtils.hasText(apiKey)) {
            logger.warn("When try update key, old key is not blank!");
        }

        this.apiKey = sessionFileService.rewriteApiKeyToFile(apiKey);

        logger.info("Api key successfully updated!");
    }

    public String getApiKey() {
        return apiKey;
    }
}
