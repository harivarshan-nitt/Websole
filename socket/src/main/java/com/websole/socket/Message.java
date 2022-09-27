package com.websole.socket;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Message {
    @JsonProperty("to")
    public String to;
    @JsonProperty("message")
    public String message;
}
