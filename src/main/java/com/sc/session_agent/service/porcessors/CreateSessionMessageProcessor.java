package com.sc.session_agent.service.porcessors;

import com.sc.session_agent.holder.SessionHolder;
import com.sc.session_agent.model.session.MessageType;
import com.sc.session_agent.model.session.client.CreateSessionClientData;
import com.sc.session_agent.model.session.server.CreateSessionServerData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CreateSessionMessageProcessor implements ClientMessageProcessor<CreateSessionServerData, CreateSessionClientData> {
    @Autowired
    private SessionHolder sessionHolder;

    @Override
    public CreateSessionServerData process(CreateSessionClientData message) {
        CreateSessionServerData serverData = new CreateSessionServerData();

        sessionHolder.updateActiveSessionKey(message.getMfcId(), message.getWindowId());
        serverData.setApiKey(sessionHolder.getApiKey());

        return serverData;
    }

    @Override
    public MessageType getProcessorType() {
        return MessageType.CREATE;
    }
}
