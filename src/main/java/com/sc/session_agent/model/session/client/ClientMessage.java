package com.sc.session_agent.model.session.client;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.sc.session_agent.model.session.Message;
import jakarta.validation.constraints.NotNull;



public class ClientMessage<T extends ClientMessageData> extends Message {
    @NotNull(message = "Data required")
    @JsonTypeInfo( use = JsonTypeInfo.Id.NAME, property = "type")
    @JsonSubTypes({
            @JsonSubTypes.Type(value = HealthCheckClientData.class, name = "healthCheckClientData"),
            @JsonSubTypes.Type(value = GetSessionClientData.class, name = "getSessionClientData"),
            @JsonSubTypes.Type(value = CreateSessionClientData.class, name = "createSessionClientData")
    })
    private T data;


    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
