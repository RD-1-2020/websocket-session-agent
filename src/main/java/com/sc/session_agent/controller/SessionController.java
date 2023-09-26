package com.sc.session_agent.controller;

import com.sc.session_agent.model.session.client.ClientMessage;
import com.sc.session_agent.model.session.server.ServerMessage;
import com.sc.session_agent.service.SessionMessageHandler;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class SessionController {
    private final static Logger logger = LoggerFactory.getLogger(SessionController.class);

    @Autowired
    private SessionMessageHandler sessionMessageHandler;

    @MessageMapping("/session")
    @SendTo("/subject")
    public ServerMessage<?> sessionEndpoint(@Valid ClientMessage<?> message) {
        try {
            return sessionMessageHandler.handle(message);
        } catch (Exception exception) {
            logger.error("Something went wrong in process get session!", exception);
            return new ServerMessage<>(exception);
        }
    }
}
