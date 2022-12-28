package com.websole.socket.messages.executor;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ExecutorResponse {
    @JsonProperty("command")
    public String command;
    @JsonProperty("clientId")
    public String clientId;

    @JsonProperty("topic")
    public String topic;

    public ExecutorResponse(String command, String clientId, String topic) {
        this.command = command;
        this.clientId = clientId;
        this.topic = topic;
    }

    public String toJson() throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(this);
    }
}