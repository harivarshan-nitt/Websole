package com.websole.socket.endpoints;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.websole.socket.database.connections.Connections;
import com.websole.socket.database.mappings.Mappings;
import com.websole.socket.messages.client.OnMessageRequest;
import com.websole.socket.messages.client.OnOpenResponse;

@ServerEndpoint(value = "/client")
public class ClientEndpoint {

    static Map<String, Session> liveSessions = new ConcurrentHashMap<>();

    @OnOpen
    public void onOpen(Session session) throws JsonProcessingException {

        synchronized (liveSessions) {
            liveSessions.put(session.getId(), session);
        }
        Connections.addEntry(session.getId(), "CLIENT");
        OnOpenResponse msg = new OnOpenResponse(Connections.getExecutors());
        session.getAsyncRemote().sendText(msg.toJson());
    }

    @OnMessage
    public void onMessage(String message, Session session) throws IOException {

        ObjectMapper mapper = new ObjectMapper();
        OnMessageRequest msg = mapper.readValue(message, OnMessageRequest.class);
        if (msg.topic.equals("COMMAND")) {
            commandExecutor(session.getId(), msg.executorId, msg.command);
        }

        if (msg.topic.equals("CONNECT")) {
            connectExecutor(session.getId(), msg.executorId);
        }
    }

    @OnClose
    public void onClose(Session session) {
        Connections.removeEntry(session.getId());
        Mappings.removeClient(session.getId());
    }

    private void connectExecutor(String clientId, String executorId) {
        Mappings.addEntry(clientId, executorId);
    }

    private void commandExecutor(String clientId, String executorId, String command) {

    }
}
