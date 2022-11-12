package com.websole.socket.messages.executor;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ExecutorRequest {
    @JsonProperty("response")
    public String response;
    @JsonProperty("clientId")
    public String clientId;
}
