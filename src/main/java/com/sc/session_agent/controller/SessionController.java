package com.sc.session_agent.controller;

import com.sc.session_agent.model.session.client.ServerMessage;
import com.sc.session_agent.model.session.server.ClientMessage;
import com.sc.session_agent.service.SessionService;
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
    private SessionService sessionService;

    @MessageMapping("/session")
    @SendTo("/get/state")
    public ServerMessage healthCheck(ClientMessage message) {
        try {
            return sessionService.processMessage(message);
        } catch (Exception exception) {
            logger.error("Something went wrong in process get session!", exception);
            return new ServerMessage();
        }
    }
}
