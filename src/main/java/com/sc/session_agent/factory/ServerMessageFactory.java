package com.sc.session_agent.factory;

import com.sc.session_agent.holder.SessionHolder;
import com.sc.session_agent.model.session.client.ClientMessage;
import com.sc.session_agent.model.session.client.ClientMessageData;
import com.sc.session_agent.model.session.server.ServerMessage;
import com.sc.session_agent.model.session.server.ServerMessageData;
import com.sc.session_agent.service.porcessors.ClientMessageProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ServerMessageFactory {
    @Autowired
    private SessionHolder sessionHolder;

    public <T extends ServerMessageData, S extends ClientMessageData> ServerMessage<T> create(ClientMessage<S> message, ClientMessageProcessor<T, S> processor) {
        ServerMessage<T> serverMessage = new ServerMessage<>();

        serverMessage.setMessageType(message.getMessageType());
        serverMessage.setData(processor.process(message.getData()));
        serverMessage.setApiKey(sessionHolder.getApiKey());

        return serverMessage;
    }
}
