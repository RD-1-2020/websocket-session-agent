package com.sc.session_agent.model.session.server;

import org.junit.jupiter.api.Test;
import org.springframework.util.StringUtils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ServerMessageTest {
    private static final String TEST_EXCEPTION_MESSAGE = "Test exception message";

    @Test
    public void testServerMessageWhenExceptionIsNullThenErrorMessageIsSet() {
        // Arrange
        Exception exception = null;

        // Act
        ServerMessage serverMessage = new ServerMessage(exception);

        // Assert
        assertTrue(StringUtils.hasText(serverMessage.getErrorMessage()));
    }

    @Test
    public void testServerMessageWhenExceptionMessageIsBlankThenErrorMessageIsSet() {
        // Arrange
        Exception exception = new Exception("");

        // Act
        ServerMessage serverMessage = new ServerMessage(exception);

        // Assert
        assertTrue(StringUtils.hasText(serverMessage.getErrorMessage()));
    }

    @Test
    public void testServerMessageWhenExceptionMessageIsNotBlankThenErrorMessageIsSet() {
        // Arrange
        Exception exception = new Exception(TEST_EXCEPTION_MESSAGE);

        // Act
        ServerMessage serverMessage = new ServerMessage(exception);

        // Assert
        assertEquals(TEST_EXCEPTION_MESSAGE, serverMessage.getErrorMessage());
    }
}