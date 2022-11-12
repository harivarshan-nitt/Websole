package com.websole.socket.endpoints;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.websole.socket.database.connections.Connections;
import com.websole.socket.database.mappings.Mappings;

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

    }

    @OnClose
    public void onClose(Session session, CloseReason closeReason) {
        Connections.removeEntry(session.getId());
        Mappings.removeExecutor(session.getId());
    }
}
