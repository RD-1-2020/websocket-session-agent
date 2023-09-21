package com.sc.session_agent.model.session.server;

public class HealthCheckServerData implements ServerMessageData {
    private boolean valid = true;
    private boolean serverAccessible = true;

    public boolean isValid() {
        return valid;
    }

    public void setValid(boolean valid) {
        this.valid = valid;
    }

    public boolean isServerAccessible() {
        return serverAccessible;
    }

    public void setServerAccessible(boolean serverAccessible) {
        this.serverAccessible = serverAccessible;
    }
}
