package com.websole.socket.messages.client;

import com.fasterxml.jackson.annotation.JsonProperty;

public class OnMessageRequest {
    @JsonProperty("topic")
    public String topic;
    @JsonProperty("command")
    public String command;
    @JsonProperty("executorId")
    public String executorId;
}
