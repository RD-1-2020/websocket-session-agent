package com.sc.session_agent.model.session.client;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.sc.session_agent.model.session.server.CreateSessionServerData;
import com.sc.session_agent.model.session.server.GetSessionServerData;
import com.sc.session_agent.model.session.server.HealthCheckServerData;

import java.io.Serializable;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = HealthCheckServerData.class, name = "healthCheckClientData"),
        @JsonSubTypes.Type(value = GetSessionServerData.class, name = "getSessionClientData"),
        @JsonSubTypes.Type(value = CreateSessionServerData.class, name = "createSessionClientData")
})
public abstract class ClientMessageData implements Serializable {
    public ClientMessageData() {}
}
