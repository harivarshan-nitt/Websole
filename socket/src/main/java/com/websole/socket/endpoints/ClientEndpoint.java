package com.websole.socket.endpoints;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.websole.socket.database.connections.ConnectionType;
import com.websole.socket.database.connections.Connections;
import com.websole.socket.database.mappings.Mappings;
import com.websole.socket.messages.client.ClientResponse;
import com.websole.socket.messages.client.OnMessageRequest;
import com.websole.socket.messages.client.Response;
import com.websole.socket.messages.client.GetExecResponse;
import com.websole.socket.messages.executor.ExecutorResponse;

@ServerEndpoint(value = "/client")
public class ClientEndpoint {

    static Map<String, Session> liveSessions = new ConcurrentHashMap<>();

    @OnOpen
    public void onOpen(Session session) throws JsonProcessingException {

        synchronized (liveSessions) {
            liveSessions.put(session.getId(), session);
        }
        Connections.addEntry(session.getId(), "CLIENT");
        sendResponse(session.getId(), new GetExecResponse(Connections.getExecutors(), "CONNECT"));
    }

    @OnMessage
    public void onMessage(String message, Session session) throws IOException {

        ObjectMapper mapper = new ObjectMapper();
        OnMessageRequest msg = mapper.readValue(message, OnMessageRequest.class);
        if (msg.topic.equals("COMMAND")) {
            if (Mappings.mappings.get(session.getId()) != null
                    && Mappings.mappings.get(session.getId()).equals(msg.executorId))
                ExecutorEndpoint.sendResponse(msg.executorId,
                        new ExecutorResponse(msg.command, session.getId(), "COMMAND"));
            else
                sendResponse(session.getId(), new Response("Connect to the requested Executor", "ERROR"));
        }

        if (msg.topic.equals("CONNECT")) {
            if (Connections.connections.get(msg.executorId) == null
                    || Connections.connections.get(msg.executorId) == ConnectionType.valueOf("CLIENT"))
                sendResponse(session.getId(), new Response("EXECUTOR NOT FOUND", "ERROR"));
            else {
                if (Mappings.mappings.get(session.getId()) != null)
                    ExecutorEndpoint.sendResponse(Mappings.mappings.get(session.getId()),
                            new ExecutorResponse("", session.getId(), "DISCONNECT"));
                Mappings.addEntry(session.getId(), msg.executorId);
                ExecutorEndpoint.sendResponse(msg.executorId, new ExecutorResponse("", session.getId(), "CONNECT"));

            }
        }
    }

    @OnClose
    public void onClose(Session session) throws JsonProcessingException {
        ExecutorEndpoint.sendResponse(Mappings.mappings.get(session.getId()),
                new ExecutorResponse("", session.getId(), "DISCONNECT"));
        Connections.removeEntry(session.getId());
        Mappings.removeClient(session.getId());
    }

    static public void sendResponse(String clientId, ClientResponse response) throws JsonProcessingException {
        liveSessions.get(clientId).getAsyncRemote().sendText(response.toJson());
    }
}
