package com.sc.session_agent.utils;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.net.URI;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
public class UriUtilsTest {
    @InjectMocks
    private UriUtils uriUtils;

    @Test
    public void testCreateWhenNoParamsThenReturnUri() {
        String url = "http://example.com";

        URI result = uriUtils.create(url);

        assertEquals(URI.create(url), result);
    }

    @Test
    public void testCreateWhenParamsThenReturnFormattedUri() {
        String url = "http://example.com/%s/%s";
        String param1 = "test1";
        String param2 = "test2";

        URI result = uriUtils.create(url, param1, param2);

        assertEquals(URI.create(String.format(url, param1, param2)), result);
    }

    @Test
    public void testCreateWhenNullUrlThenThrowException() {
        String url = null;

        assertThrows(NullPointerException.class, () -> uriUtils.create(url));
    }
}