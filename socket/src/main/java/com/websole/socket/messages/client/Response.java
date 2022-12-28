package com.websole.socket.messages.client;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Response implements ClientResponse {
    @JsonProperty("response")
    public String response;

    @JsonProperty("topic")
    public String topic;

    public Response(String response, String topic) {
        this.response = response;
        this.topic = topic;
    }

    public String toJson() throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(this);
    }
}
