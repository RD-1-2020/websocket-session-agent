package com.sc.session_agent;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

@DirtiesContext
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SessionAgentApplicationTests {

    @Test
    public void testMainWhenArgsAreEmptyThenApplicationStartsSuccessfully() {
        // Arrange
        String[] args = new String[]{};

        // Act
        SessionAgentApplication.main(args);

        // Assert
        // No exceptions should be thrown, so no assertions are needed.
    }

    @Test
    void contextLoads() {
    }
}