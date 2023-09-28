
package com.sc.session_agent.factory;

import com.sc.session_agent.holder.SessionHolder;
import com.sc.session_agent.model.session.MessageType;
import com.sc.session_agent.model.session.client.ClientMessage;
import com.sc.session_agent.model.session.client.HealthCheckClientData;
import com.sc.session_agent.model.session.server.HealthCheckServerData;
import com.sc.session_agent.model.session.server.ServerMessage;
import com.sc.session_agent.service.porcessors.ClientMessageProcessor;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ServerMessageFactoryTest {
    private static final String API_KEY = "apiKey";

    @Mock
    private SessionHolder sessionHolder;

    @Mock
    private ClientMessageProcessor clientMessageProcessor;

    @InjectMocks
    private ServerMessageFactory serverMessageFactory;

    @Mock
    private ClientMessage clientMessage;

    @Mock
    private HealthCheckClientData healthCheckClientData;

    @Mock
    private HealthCheckServerData healthCheckServerData;

    @Test
    public void testCreateWhenGivenClientMessageAndProcessorThenReturnsCorrectServerMessage() {
        when(clientMessage.getData()).thenReturn(healthCheckClientData);
        when(clientMessage.getMessageType()).thenReturn(MessageType.GET);
        when(clientMessageProcessor.process(clientMessage.getData())).thenReturn(healthCheckServerData);
        when(sessionHolder.getApiKey()).thenReturn(API_KEY);

        ServerMessage serverMessage = serverMessageFactory.create(clientMessage, clientMessageProcessor);

        assertThat(serverMessage.getMessageType()).isEqualTo(clientMessage.getMessageType());
        assertThat(serverMessage.getData()).isEqualTo(healthCheckServerData);
        assertThat(serverMessage.getApiKey()).isEqualTo(API_KEY);
    }
}