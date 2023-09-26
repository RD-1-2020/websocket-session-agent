package com.sc.session_agent.service.porcessors;

import com.sc.session_agent.holder.SessionHolder;
import com.sc.session_agent.model.session.MessageType;
import com.sc.session_agent.model.session.client.CreateSessionClientData;
import com.sc.session_agent.model.session.server.CreateSessionServerData;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CreateSessionMessageProcessorTest {

    @InjectMocks
    private CreateSessionMessageProcessor createSessionMessageProcessor;

    @Mock
    private SessionHolder sessionHolder;

    @Mock
    private CreateSessionClientData clientData;

    @Test
    public void testGetProcessorTypeReturnsCreate() {
        // Arrange
        // No arrangement needed as the method doesn't have any dependencies

        // Act
        MessageType result = createSessionMessageProcessor.getProcessorType();

        // Assert
        assertThat(result).isEqualTo(MessageType.CREATE);
    }

    @Test
    public void testProcessWhenCalledThenUpdatesActiveSessionKeyAndSetsApiKey() {
        // Arrange
        String apiKey = "testApiKey";
        when(sessionHolder.getApiKey()).thenReturn(apiKey);

        // Act
        CreateSessionServerData serverData = createSessionMessageProcessor.process(clientData);

        // Assert
        verify(sessionHolder).updateActiveSessionKey(clientData.getMfcId(), clientData.getWindowId());
        verify(sessionHolder).getApiKey();
        assertThat(serverData.getApiKey()).isEqualTo(apiKey);
    }

    @Test
    public void testProcessWhenCalledThenCallsUpdateActiveSessionKey() {
        // Act
        createSessionMessageProcessor.process(clientData);

        // Assert
        verify(sessionHolder).updateActiveSessionKey(clientData.getMfcId(), clientData.getWindowId());
    }

    @Test
    public void testProcessWhenCalledThenCallsGetApiKey() {
        // Act
        createSessionMessageProcessor.process(clientData);

        // Assert
        verify(sessionHolder).getApiKey();
    }
}