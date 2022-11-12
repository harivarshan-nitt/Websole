package com.websole.socket.messages.client;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class OnOpenResponse {
    @JsonProperty("liveExecutors")
    public List<String> liveExecutors;

    public OnOpenResponse(List<String> executors) {
        this.liveExecutors = executors;
    }

    public String toJson() throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(this);
    }
}
