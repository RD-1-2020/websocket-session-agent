package com.sc.session_agent.controller;

import com.sc.session_agent.model.session.client.CreateServerMessage;
import com.sc.session_agent.model.session.client.GetServerMessage;
import com.sc.session_agent.model.session.client.HealthCheckServerMessage;
import com.sc.session_agent.model.session.server.CreateClientMessage;
import com.sc.session_agent.model.session.server.GetClientMessage;
import com.sc.session_agent.model.session.server.HealthCheckClientMessage;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class SessionController {
    @MessageMapping("/session/get")
    @SendTo("/get/state")
    public GetServerMessage getSession(GetClientMessage message) {

        return new GetServerMessage();
    }

    @MessageMapping("/session/create")
    @SendTo("/create/state")
    public CreateServerMessage createSession(CreateClientMessage message) {

        return new CreateServerMessage();
    }

    @MessageMapping("/session/health/check")
    @SendTo("/health/state")
    public HealthCheckServerMessage getSession(HealthCheckClientMessage message) {

        return new HealthCheckServerMessage();
    }
}
