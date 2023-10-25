package com.sc.session_agent.model.session.client;

import com.fasterxml.jackson.annotation.JsonTypeName;

@JsonTypeName("createSessionClientData")
public class CreateSessionClientData extends ClientMessageData {
    private Long windowId;
    private Long mfcId;

    public Long getWindowId() {
        return windowId;
    }

    public void setWindowId(Long windowId) {
        this.windowId = windowId;
    }

    public Long getMfcId() {
        return mfcId;
    }

    public void setMfcId(Long mfcId) {
        this.mfcId = mfcId;
    }
}
