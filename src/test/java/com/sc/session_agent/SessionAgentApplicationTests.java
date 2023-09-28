package com.sc.session_agent;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

@DirtiesContext
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SessionAgentApplicationTests {

    @Test
    public void testMainWhenArgsAreEmptyThenApplicationStartsSuccessfully() {
        String[] args = new String[]{};

        SessionAgentApplication.main(args);
    }

    @Test
    void contextLoads() {
    }
}