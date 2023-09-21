package com.sc.session_agent.service;

import com.sc.session_agent.model.session.MessageType;
import com.sc.session_agent.model.session.client.ClientMessageData;
import com.sc.session_agent.model.session.server.ServerMessageData;

public interface ClientMessageProcessor<T extends ServerMessageData, S extends ClientMessageData> {
    T process(S message);

    MessageType getProcessorType();
}
