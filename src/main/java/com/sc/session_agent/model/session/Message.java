package com.sc.session_agent.model.session;

import jakarta.validation.constraints.NotNull;

public class Message {
    @NotNull(message = "messageType required")
    private MessageType messageType;

    public Message() {
    }

    public MessageType getMessageType() {
        return messageType;
    }

    public void setMessageType(MessageType messageType) {
        this.messageType = messageType;
    }
}
