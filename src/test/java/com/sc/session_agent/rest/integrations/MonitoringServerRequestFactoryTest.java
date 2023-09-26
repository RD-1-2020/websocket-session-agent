package com.sc.session_agent.rest.integrations;

import com.sc.session_agent.utils.UriUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.net.URI;
import java.net.http.HttpRequest;
import java.time.Duration;
import java.time.temporal.ChronoUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class MonitoringServerRequestFactoryTest {
    private static final String BASE_URL = "http://example.com/";
    private static final URI EXPECTED_URI_CREATE = URI.create(BASE_URL + "session/api/create?mfcId=1&windowId=2");
    private static final URI EXPECTED_URI_HEALTH_CHECK = URI.create(BASE_URL + "session/health/check?mfcId=1&windowId=2");

    private static final long MFC_ID = 1L;
    private static final long WINDOW_ID = 2L;
    private static final String API_KEY = "testApiKey";

    @Mock
    private UriUtils uriUtils;

    @InjectMocks
    private MonitoringServerRequestFactory monitoringServerRequestFactory;

    private static final Duration TIMEOUT = Duration.of(10_000L, ChronoUnit.SECONDS);

    @BeforeEach
    public void setUp() {
        ReflectionTestUtils.setField(monitoringServerRequestFactory, "monitoringServerUrl", BASE_URL);
    }


    @Test
    public void testCreateHealthCheckRequestWhenApiKeyProvidedThenReturnsHttpRequestWithCorrectUriAndTimeout() {
        // Arrange
        when(uriUtils.create(any(), any())).thenReturn(EXPECTED_URI_HEALTH_CHECK);

        // Act
        HttpRequest result = monitoringServerRequestFactory.createHealthCheckRequest(API_KEY);

        // Assert
        assertEquals(EXPECTED_URI_HEALTH_CHECK, result.uri());
        assertEquals(TIMEOUT, result.timeout().get());
    }

    @Test
    public void testCreateHealthCheckRequestWhenApiKeyIsNullThenThrowsException() {
        // Arrange
        when(uriUtils.create(any(), any())).thenReturn(null);

        // Act & Assert
        assertThrows(NullPointerException.class, () -> monitoringServerRequestFactory.createHealthCheckRequest(null));
    }

    @Test
    public void testCreateApiKeyGenerateRequestWhenGivenMfcIdAndWindowIdThenHttpRequestIsCorrectlyBuilt() {
        // Arrange
        when(uriUtils.create(any(), any(), any())).thenReturn(EXPECTED_URI_CREATE);

        // Act
        HttpRequest result = monitoringServerRequestFactory.createApiKeyGenerateRequest(MFC_ID, WINDOW_ID);

        // Assert
        assertEquals(EXPECTED_URI_CREATE, result.uri());
        assertEquals(TIMEOUT, result.timeout().get());
    }

    @Test
    public void testCreateApiKeyGenerateRequestWhenUriUtilsCreateThrowsExceptionThenExceptionIsPropagated() {
        // Arrange
        when(uriUtils.create(any(), any(), any())).thenReturn(null);

        // Act & Assert
        assertThrows(NullPointerException.class, () -> monitoringServerRequestFactory.createApiKeyGenerateRequest(MFC_ID, WINDOW_ID));
    }

    @Test
    public void testCreateApiKeyGenerateRequestWhenMfcIdAndWindowIdProvidedThenHttpRequestCreated() {
        // Arrange
        when(uriUtils.create(any(), any(), any())).thenReturn(EXPECTED_URI_CREATE);

        // Act
        HttpRequest result = monitoringServerRequestFactory.createApiKeyGenerateRequest(MFC_ID, WINDOW_ID);

        // Assert
        assertEquals(EXPECTED_URI_CREATE, result.uri());
        assertEquals(TIMEOUT, result.timeout().get());
    }

    @Test
    public void testCreateApiKeyGenerateRequestWhenMfcIdOrWindowIdIsNullThenHttpRequestCreated() {
        // Arrange
        when(uriUtils.create(any(), any(), any())).thenReturn(EXPECTED_URI_CREATE);

        // Act
        HttpRequest result = monitoringServerRequestFactory.createApiKeyGenerateRequest(MFC_ID, WINDOW_ID);

        // Assert
        assertEquals(EXPECTED_URI_CREATE, result.uri());
        assertEquals(TIMEOUT, result.timeout().get());
    }
}