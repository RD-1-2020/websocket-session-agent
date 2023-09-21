package com.sc.session_agent.model.session.client;

public class CreateSessionClientData implements ClientMessageData {
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
