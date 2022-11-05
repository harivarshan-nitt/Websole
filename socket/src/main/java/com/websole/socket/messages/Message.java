package com.websole.socket.messages;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Message {
    @JsonProperty("to")
    public String to;
    @JsonProperty("message")
    public String message;
}
