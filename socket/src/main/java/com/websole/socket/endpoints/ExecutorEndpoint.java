package com.websole.socket.endpoints;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.websole.socket.database.connections.Connections;
import com.websole.socket.database.mappings.Mappings;
import com.websole.socket.messages.client.Response;
import com.websole.socket.messages.executor.ExecutorRequest;
import com.websole.socket.messages.executor.ExecutorResponse;

@ServerEndpoint(value = "/executor")
public class ExecutorEndpoint {

    static Map<String, Session> liveSessions = new ConcurrentHashMap<>();

    @OnOpen
    public void onOpen(Session session) {

        synchronized (liveSessions) {
            liveSessions.put(session.getId(), session);
        }
        Connections.addEntry(session.getId(), "EXECUTOR");
    }

    @OnMessage
    public void onMessage(String message, Session session) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        ExecutorRequest msg = mapper.readValue(message, ExecutorRequest.class);
        ClientEndpoint.sendResponse(msg.clientId, new Response(msg.response, "RESPONSE"));
    }

    @OnClose
    public void onClose(Session session, CloseReason closeReason) throws JsonProcessingException {
        List<String> clients = Mappings.getClients(session.getId());
        for (int i = 0; i < clients.size(); i++) {
            ClientEndpoint.sendResponse(clients.get(i), new Response("Executor is down", "ERROR"));
        }
        Connections.removeEntry(session.getId());
        Mappings.removeExecutor(session.getId());
    }

    static public void sendResponse(String executorId, ExecutorResponse response) throws JsonProcessingException {
        liveSessions.get(executorId).getAsyncRemote().sendText(response.toJson());
    }
}
