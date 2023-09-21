package com.sc.session_agent.model.session.server;

import com.sc.session_agent.model.session.Message;
import org.springframework.util.StringUtils;

public class ServerMessage<T extends ServerMessageData> extends Message {
    private static final String EXCEPTION_BLANK_MESSAGE = "Exception or exception message is blank. Connect with support psl.";
    private String errorMessage;

    private T data;
    private String apiKey;

    public ServerMessage(Exception exception) {
        if (exception == null || !StringUtils.hasText(exception.getMessage())) {
            this.errorMessage = EXCEPTION_BLANK_MESSAGE;
            return;
        }

        this.errorMessage = exception.getMessage();
    }

    public ServerMessage() {
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }
}
