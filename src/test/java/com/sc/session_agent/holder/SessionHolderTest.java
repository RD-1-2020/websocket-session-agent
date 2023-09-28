package com.sc.session_agent.holder;

import com.sc.session_agent.model.session.server.HealthCheckServerData;
import com.sc.session_agent.rest.integrations.MonitoringServerIntegration;
import com.sc.session_agent.service.SessionFileService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.test.util.ReflectionTestUtils;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class SessionHolderTest {
    private static final String TEST_API_KEY = "testApiKey";
    private static final long MFC_ID = 1L;
    private static final long WINDOW_ID = 1L;

    @Mock
    private SessionFileService sessionFileService;

    @Mock
    private MonitoringServerIntegration monitoringServerIntegration;

    @InjectMocks
    private SessionHolder sessionHolder;

    @BeforeEach
    public void setUp() {
        when(sessionFileService.getApiKeyFromSessionFile()).thenReturn(TEST_API_KEY);
        when(monitoringServerIntegration.createApiKey(MFC_ID, WINDOW_ID)).thenReturn(TEST_API_KEY);
        when(sessionFileService.rewriteApiKeyToFile(TEST_API_KEY)).thenReturn(TEST_API_KEY);

        ReflectionTestUtils.setField(sessionHolder, "apiKey", TEST_API_KEY);
    }

    @Test
    public void testHealthCheckWhenCalledThenMonitoringServerIntegrationIsCalledWithCorrectArgument() throws Exception {
        when(monitoringServerIntegration.healthCheck(TEST_API_KEY)).thenReturn(true);

        sessionHolder.healthCheck();

        verify(monitoringServerIntegration).healthCheck(TEST_API_KEY);
    }

    @Test
    public void testHealthCheckWhenMonitoringServerIntegrationThrowsExceptionThenServerAccessibleIsFalse() throws Exception {
        doThrow(new RuntimeException()).when(monitoringServerIntegration).healthCheck(TEST_API_KEY);

        HealthCheckServerData result = sessionHolder.healthCheck();

        assertThat(result.isServerAccessible()).isFalse();
    }

    @Test
    public void testHealthCheckWhenApiKeyIsValidThenReturnValid() throws Exception {
        when(monitoringServerIntegration.healthCheck(TEST_API_KEY)).thenReturn(true);

        HealthCheckServerData result = sessionHolder.healthCheck();

        assertThat(result.isValid()).isTrue();
        assertThat(result.isServerAccessible()).isTrue();
    }

    @Test
    public void testHealthCheckWhenApiKeyIsInvalidThenReturnServerNotAccessible() throws Exception {
        doThrow(new RuntimeException()).when(monitoringServerIntegration).healthCheck(TEST_API_KEY);

        HealthCheckServerData result = sessionHolder.healthCheck();

        assertThat(result.isServerAccessible()).isFalse();
    }

    @Test
    public void testInitWhenCalledThenApiKeyIsInitialized() {
        sessionHolder.init();

        assertThat(sessionHolder.getApiKey()).isEqualTo(TEST_API_KEY);
    }

    @Test
    public void testUpdateActiveSessionKeyWhenApiKeyNotBlank() {
        sessionHolder.init();

        sessionHolder.updateActiveSessionKey(MFC_ID, WINDOW_ID);

        assertThat(sessionHolder.getApiKey()).isEqualTo(TEST_API_KEY);
    }

    @Test
    public void testUpdateActiveSessionKeyWhenApiKeyBlank() {
        when(sessionFileService.getApiKeyFromSessionFile()).thenReturn("");
        when(monitoringServerIntegration.createApiKey(MFC_ID, WINDOW_ID)).thenReturn(TEST_API_KEY);
        sessionHolder.init();

        sessionHolder.updateActiveSessionKey(MFC_ID, WINDOW_ID);

        assertThat(sessionHolder.getApiKey()).isEqualTo(TEST_API_KEY);
    }
}