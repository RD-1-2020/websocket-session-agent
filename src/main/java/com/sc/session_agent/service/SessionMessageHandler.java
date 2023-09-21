package com.sc.session_agent.service;

import com.sc.session_agent.factory.ServerMessageFactory;
import com.sc.session_agent.model.session.client.ClientMessage;
import com.sc.session_agent.model.session.server.ServerMessage;
import com.sc.session_agent.service.porcessors.ClientMessageProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SessionMessageHandler {
    @Autowired
    private List<ClientMessageProcessor> clientMessageProcessors;

    @Autowired
    private ServerMessageFactory serverMessageFactory;

    public ServerMessage<?> handle(ClientMessage<?> message) {
        return clientMessageProcessors
                .stream()
                .filter(processor -> message.getMessageType().equals(processor.getProcessorType()))
                .map(processor -> serverMessageFactory.create(message, processor))
                .findFirst().orElseThrow(() -> new RuntimeException("No processor for " + message.getMessageType() + " type!"));
    }
}
