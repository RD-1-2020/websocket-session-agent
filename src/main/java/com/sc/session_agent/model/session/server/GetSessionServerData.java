package com.sc.session_agent.model.session.server;

public class GetSessionServerData extends ServerMessageData {
    private String apiKey;

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }
}
