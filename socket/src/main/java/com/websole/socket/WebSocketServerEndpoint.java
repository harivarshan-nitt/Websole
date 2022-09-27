package com.websole.socket;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Future;

@ServerEndpoint(value = "/test")
public class WebSocketServerEndpoint {

    static Map<String, Session> liveSessions = new ConcurrentHashMap<>();

    @OnOpen
    public void onOpen(Session session) {

        synchronized (liveSessions) {
            liveSessions.put(session.getId(), session);
        }

        Future<Void> deliveryTracker = session.getAsyncRemote().sendText("Connection Id - " + session.getId());

    }

    @OnMessage
    public void onMessage(String message, Session session) throws IOException {

        ObjectMapper mapper = new ObjectMapper();
        Message msg = mapper.readValue(message, Message.class);

        System.out.println(msg);
        synchronized (liveSessions) {
            Future<Void> deliveryTracker = liveSessions.get(msg.to).getAsyncRemote().sendText(msg.message);
        }
    }

    @OnClose
    public void onClose(Session session, CloseReason closeReason) {
        System.out.println("[SERVER]: Session " + session.getId() + " closed, because " + closeReason);
    }
}
