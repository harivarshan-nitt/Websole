package com.websole.socket.messages.client;

import com.fasterxml.jackson.core.JsonProcessingException;

public interface ClientResponse {
    String toJson() throws JsonProcessingException;
}
