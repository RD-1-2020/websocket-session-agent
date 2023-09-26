package com.sc.session_agent.utils;

import org.springframework.stereotype.Service;

import java.net.URI;

@Service
public class UriUtils {
    public URI create(String url, Object... params) {
        if (params == null || params.length == 0) {
            return URI.create(url);
        }

        return URI.create(String.format(url, params));
    }
}
