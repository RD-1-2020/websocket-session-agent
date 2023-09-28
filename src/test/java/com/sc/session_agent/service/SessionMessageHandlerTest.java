package com.sc.session_agent.service;

import com.sc.session_agent.factory.ServerMessageFactory;
import com.sc.session_agent.model.session.MessageType;
import com.sc.session_agent.model.session.client.ClientMessage;
import com.sc.session_agent.model.session.server.ServerMessage;
import com.sc.session_agent.service.porcessors.ClientMessageProcessor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class SessionMessageHandlerTest {

    @Mock
    private List<ClientMessageProcessor> clientMessageProcessors;

    @Mock
    private ServerMessageFactory serverMessageFactory;

    @Mock
    private ClientMessageProcessor mockProcessor;

    @Mock
    private ClientMessage mockClientMessage;

    @Mock
    private ServerMessage mockServerMessage;

    @InjectMocks
    private SessionMessageHandler sessionMessageHandler;

    @BeforeEach
    public void setUp() {
        when(clientMessageProcessors.stream()).thenReturn(Collections.singletonList(mockProcessor).stream());
        when(mockClientMessage.getMessageType()).thenReturn(MessageType.HEALTH_CHECK);
    }

    @Test
    public void testHandleWhenProcessorExistsThenReturnsCorrectServerMessage() {
        when(mockProcessor.getProcessorType()).thenReturn(MessageType.HEALTH_CHECK);
        when(serverMessageFactory.create(mockClientMessage, mockProcessor)).thenReturn(mockServerMessage);

        ServerMessage result = sessionMessageHandler.handle(mockClientMessage);

        assertEquals(mockServerMessage, result);
    }

    @Test
    public void testHandleWhenNoProcessorExistsThenThrowsException() {
        assertThrows(RuntimeException.class, () -> sessionMessageHandler.handle(mockClientMessage));
    }
}