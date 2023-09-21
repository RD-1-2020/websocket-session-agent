package com.sc.session_agent.rest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Service
public class SupportSectorIntegration {


    public String createApiKey(Long mfcId, Long windowId) {
        HttpResponse<String> responseWithApiKey = HttpRequest.newBuilder().uri()
    }
}
