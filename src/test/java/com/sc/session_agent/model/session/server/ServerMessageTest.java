package com.sc.session_agent.model.session.server;

import org.junit.jupiter.api.Test;
import org.springframework.util.StringUtils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ServerMessageTest {
    private static final String TEST_EXCEPTION_MESSAGE = "Test exception message";

    @Test
    public void testServerMessageWhenExceptionIsNullThenErrorMessageIsSet() {
        Exception exception = null;

        ServerMessage serverMessage = new ServerMessage(exception);

        assertTrue(StringUtils.hasText(serverMessage.getErrorMessage()));
    }

    @Test
    public void testServerMessageWhenExceptionMessageIsBlankThenErrorMessageIsSet() {
        Exception exception = new Exception("");

        ServerMessage serverMessage = new ServerMessage(exception);

        assertTrue(StringUtils.hasText(serverMessage.getErrorMessage()));
    }

    @Test
    public void testServerMessageWhenExceptionMessageIsNotBlankThenErrorMessageIsSet() {
        Exception exception = new Exception(TEST_EXCEPTION_MESSAGE);

        ServerMessage serverMessage = new ServerMessage(exception);

        assertEquals(TEST_EXCEPTION_MESSAGE, serverMessage.getErrorMessage());
    }
}