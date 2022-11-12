package com.websole.socket.messages.client;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class GetExecResponse implements ClientResponse {
    @JsonProperty("liveExecutors")
    public List<String> liveExecutors;

    @JsonProperty("topic")
    public String topic;

    public GetExecResponse(List<String> executors, String topic) {
        this.liveExecutors = executors;
        this.topic = topic;
    }

    public String toJson() throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(this);
    }
}
