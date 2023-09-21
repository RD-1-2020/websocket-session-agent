package com.sc.session_agent.service.porcessors;

import com.sc.session_agent.holder.SessionHolder;
import com.sc.session_agent.model.session.MessageType;
import com.sc.session_agent.model.session.client.GetSessionClientData;
import com.sc.session_agent.model.session.server.GetSessionServerData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GetSessionMessageProcessor implements ClientMessageProcessor<GetSessionServerData, GetSessionClientData> {
    @Autowired
    private SessionHolder sessionHolder;

    @Override
    public GetSessionServerData process(GetSessionClientData message) {
        GetSessionServerData serverData = new GetSessionServerData();

        serverData.setApiKey(sessionHolder.getApiKey());

        return serverData;
    }

    @Override
    public MessageType getProcessorType() {
        return MessageType.GET;
    }
}
