package com.sc.session_agent.service.porcessors;

import com.sc.session_agent.holder.SessionHolder;
import com.sc.session_agent.model.session.MessageType;
import com.sc.session_agent.model.session.client.GetSessionClientData;
import com.sc.session_agent.model.session.server.GetSessionServerData;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class GetSessionMessageProcessorTest {

    @Mock
    private SessionHolder sessionHolder;

    @InjectMocks
    private GetSessionMessageProcessor getSessionMessageProcessor;

    @Mock
    private GetSessionClientData getSessionClientData;

    @Test
    public void testProcessWhenCalledThenApiKeyIsSet() {
        // Arrange
        String expectedApiKey = "testApiKey";
        when(sessionHolder.getApiKey()).thenReturn(expectedApiKey);

        // Act
        GetSessionServerData result = getSessionMessageProcessor.process(getSessionClientData);

        // Assert
        assertThat(result.getApiKey()).isEqualTo(expectedApiKey);
    }

    @Test
    public void testGetProcessorTypeReturnsGet() {
        // Arrange
        MessageType expectedType = MessageType.GET;

        // Act
        MessageType result = getSessionMessageProcessor.getProcessorType();

        // Assert
        assertThat(result).isEqualTo(expectedType);
    }
}