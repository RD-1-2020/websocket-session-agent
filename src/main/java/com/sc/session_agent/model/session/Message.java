package com.sc.session_agent.model.session;

public class Message {
    private String errorMessage;

    private MessageType messageType;

    public Message(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public Message() {
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public MessageType getMessageType() {
        return messageType;
    }

    public void setMessageType(MessageType messageType) {
        this.messageType = messageType;
    }
}
