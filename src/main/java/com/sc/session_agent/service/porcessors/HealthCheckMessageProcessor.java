package com.sc.session_agent.service.porcessors;

import com.sc.session_agent.holder.SessionHolder;
import com.sc.session_agent.model.session.MessageType;
import com.sc.session_agent.model.session.client.HealthCheckClientData;
import com.sc.session_agent.model.session.server.HealthCheckServerData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HealthCheckMessageProcessor implements ClientMessageProcessor<HealthCheckServerData, HealthCheckClientData> {
    @Autowired
    private SessionHolder sessionHolder;

    @Override
    public HealthCheckServerData process(HealthCheckClientData message) {
        return sessionHolder.healthCheck();
    }

    @Override
    public MessageType getProcessorType() {
        return MessageType.HEALTH_CHECK;
    }
}
