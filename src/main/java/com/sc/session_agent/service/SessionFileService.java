package com.sc.session_agent.service;

import com.sc.session_agent.utils.FileBinaryUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

@Service
public class SessionFileService {
    private final static Logger logger = LoggerFactory.getLogger(SessionFileService.class);

    @Value("${session.storage.file.path:./auth.data}")
    private String authFilePath;

    @Autowired
    private FileBinaryUtil fileBinaryUtil;

    public String rewriteApiKeyToFile(String apiKey) {
        logger.info("Try update api key in session file...");

        File sessionFile = getSessionFile();
        try {
            Files.deleteIfExists(sessionFile.toPath());
            sessionFile.createNewFile();
            fileBinaryUtil.writeBinary(sessionFile, List.of(apiKey));

            logger.info("Api key in session file successfully updated!");

            return apiKey;
        } catch (IOException exception) {
            logger.error("Something went wrong in process update auth sessionFile!", exception);
            return null;
        }
    }

    public String getApiKeyFromSessionFile() {
        logger.info("Try get api key from session file...");

        File sessionFile = getSessionFile();
        if (!sessionFile.exists()) {
            logger.info("Session file is not exist, skip try get api key...");
            return null;
        }

        try {
            return fileBinaryUtil.readBinary(sessionFile).stream().findFirst().orElse(null);
        } catch (IOException exception) {
            logger.error("Something went wrong in process get api key from sessionFile!", exception);
            return null;
        }
    }

    private File getSessionFile() {
        return new File(authFilePath);
    }
}
