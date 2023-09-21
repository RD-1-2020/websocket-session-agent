package com.sc.session_agent.model.session.client;

import com.sc.session_agent.model.session.Message;
import jakarta.validation.constraints.NotNull;

public class ClientMessage<T extends ClientMessageData> extends Message {
    @NotNull(message = "Data required")
    private T data;

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
