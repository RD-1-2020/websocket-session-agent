package com.sc.session_agent.model.session.server;

import com.sc.session_agent.model.session.Message;
import com.sc.session_agent.model.session.client.ClientMessageData;

public class ClientMessage<T extends ClientMessageData> extends Message {
    private T data;

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
