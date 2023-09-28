package com.sc.session_agent.controller;

import com.sc.session_agent.model.session.client.ClientMessage;
import com.sc.session_agent.model.session.server.ServerMessage;
import com.sc.session_agent.service.SessionMessageHandler;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class SessionControllerTest {

    @Mock
    private SessionMessageHandler sessionMessageHandler;

    @InjectMocks
    private SessionController sessionController;

    @Mock
    private ClientMessage clientMessage;

    @Mock
    private ServerMessage serverMessage;

    @Test
    public void testSessionEndpointWhenValidMessageThenReturnServerMessage() {
        when(sessionMessageHandler.handle(clientMessage)).thenReturn(serverMessage);

        ServerMessage<?> result = sessionController.sessionEndpoint(clientMessage);

        assertEquals(serverMessage, result);
        verify(sessionMessageHandler, times(1)).handle(clientMessage);
    }

    @Test
    public void testSessionEndpointWhenExceptionThrownThenReturnServerMessageWithException() {
        Exception exception = new RuntimeException("Test exception");
        when(sessionMessageHandler.handle(clientMessage)).thenThrow(exception);

        ServerMessage<?> result = sessionController.sessionEndpoint(clientMessage);

        assertEquals("Test exception", result.getErrorMessage());
        verify(sessionMessageHandler, times(1)).handle(clientMessage);
    }
}