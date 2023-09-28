package com.sc.session_agent.service.porcessors;

import com.sc.session_agent.holder.SessionHolder;
import com.sc.session_agent.model.session.MessageType;
import com.sc.session_agent.model.session.client.HealthCheckClientData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class HealthCheckMessageProcessorTest {

    @InjectMocks
    private HealthCheckMessageProcessor healthCheckMessageProcessor;

    @Mock
    private SessionHolder sessionHolder;

    @Test
    public void testGetProcessorTypeReturnsHealthCheck() {
        MessageType result = healthCheckMessageProcessor.getProcessorType();

        Assertions.assertEquals(MessageType.HEALTH_CHECK, result, "getProcessorType() should return HEALTH_CHECK");
    }

    @Test
    public void testProcessWhenCalledThenCallsSessionHolderHealthCheck() {
        HealthCheckClientData message = new HealthCheckClientData();

        healthCheckMessageProcessor.process(message);

        verify(sessionHolder).healthCheck();
    }
}