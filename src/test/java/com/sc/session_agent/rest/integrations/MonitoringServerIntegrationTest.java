package com.sc.session_agent.rest.integrations;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class MonitoringServerIntegrationTest {
    private static final long MFC_ID = 1L;
    private static final long WINDOW_ID = 2L;
    private static final String TEST_API_KEY = "apiKey";
    private static final String EXPECTED_RESPONSE = TEST_API_KEY;
    public static final String RESPONSE_BODY = "true";


    private MockedStatic<HttpClient> httpClientMockedStatic;

    @Mock
    private MonitoringServerRequestFactory monitoringServerRequestFactory;

    @InjectMocks
    private MonitoringServerIntegration monitoringServerIntegration;

    @Mock
    private HttpRequest request;

    @Mock
    private HttpResponse<String> response;

    @Mock
    private HttpClient httpClient;

    @Mock
    private HttpClient.Builder httpClientBuilder;

    @Test
    public void testHealthCheckWhenHttpClientReturnsSuccessThenReturnsCorrectBoolean() throws Exception {
        when(monitoringServerRequestFactory.createHealthCheckRequest(TEST_API_KEY)).thenReturn(request);
        when(httpClient.send(request, HttpResponse.BodyHandlers.ofString())).thenReturn(response);
        when(response.body()).thenReturn(RESPONSE_BODY);

        Boolean actualResponse = monitoringServerIntegration.healthCheck(TEST_API_KEY);

        verify(httpClient).send(request, HttpResponse.BodyHandlers.ofString());
        assertThat(actualResponse).isEqualTo(Boolean.parseBoolean(RESPONSE_BODY));
    }

    @Test
    public void testHealthCheckWhenHttpClientThrowsExceptionThenThrowsException() throws Exception {
        when(monitoringServerRequestFactory.createHealthCheckRequest(TEST_API_KEY)).thenReturn(request);
        when(httpClient.send(request, HttpResponse.BodyHandlers.ofString())).thenThrow(new RuntimeException("Error during health check"));

        assertThatThrownBy(() -> monitoringServerIntegration.healthCheck(TEST_API_KEY))
                .isInstanceOf(Exception.class)
                .hasMessageContaining("Error during health check");
    }

    @BeforeEach
    public void setUp() throws IOException, InterruptedException {
        httpClientMockedStatic = Mockito.mockStatic(HttpClient.class);
        httpClientMockedStatic.when(HttpClient::newBuilder).thenReturn(httpClientBuilder);
        when(httpClientBuilder.build()).thenReturn(httpClient);

        when(monitoringServerRequestFactory.createApiKeyGenerateRequest(MFC_ID, WINDOW_ID)).thenReturn(request);
        when(httpClient.send(request, HttpResponse.BodyHandlers.ofString())).thenReturn(response);
        when(response.body()).thenReturn(EXPECTED_RESPONSE);
    }

    @AfterEach
    public void tearDown() {
        httpClientMockedStatic.close();
    }

    @Test
    public void testCreateApiKeyWhenMfcIdAndWindowIdValidThenReturnResponseBody() throws Exception {
        String actualResponse = monitoringServerIntegration.createApiKey(MFC_ID, WINDOW_ID);

        verify(httpClient).send(request, HttpResponse.BodyHandlers.ofString());
        assertThat(actualResponse).isEqualTo(EXPECTED_RESPONSE);
    }

    @Test
    public void testCreateApiKeyWhenExceptionOccursThenReturnNull() throws Exception {
        when(monitoringServerRequestFactory.createApiKeyGenerateRequest(MFC_ID, WINDOW_ID)).thenReturn(request);
        when(httpClient.send(request, HttpResponse.BodyHandlers.ofString())).thenThrow(new RuntimeException());

        String actualResponse = monitoringServerIntegration.createApiKey(MFC_ID, WINDOW_ID);

        assertThat(actualResponse).isNull();
    }

    @Test
    public void testCreateApiKeyWhenValidIdsThenReturnsExpectedResponse() throws Exception {
        String actualResponse = monitoringServerIntegration.createApiKey(MFC_ID, WINDOW_ID);

        verify(httpClient).send(request, HttpResponse.BodyHandlers.ofString());
        assertThat(actualResponse).isEqualTo(EXPECTED_RESPONSE);
    }

    @Test
    public void testCreateApiKeyWhenExceptionThenLogsErrorAndReturnsNull() throws Exception {
        when(monitoringServerRequestFactory.createApiKeyGenerateRequest(MFC_ID, WINDOW_ID)).thenReturn(request);
        when(httpClient.send(request, HttpResponse.BodyHandlers.ofString())).thenThrow(new RuntimeException());

        String actualResponse = monitoringServerIntegration.createApiKey(MFC_ID, WINDOW_ID);

        assertThat(actualResponse).isNull();
    }
}