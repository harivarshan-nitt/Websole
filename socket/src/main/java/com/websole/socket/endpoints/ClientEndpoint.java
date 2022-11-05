package com.websole.socket.endpoints;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.websole.socket.database.connections.Connection;
import com.websole.socket.database.connections.ConnectionRespository;
import com.websole.socket.messages.Message;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@ServerEndpoint(value = "/client")
public class ClientEndpoint {

    static Map<String, Session> liveSessions = new ConcurrentHashMap<>();

    @Autowired
    ConnectionRespository connectionRespository;

    @OnOpen
    public void onOpen(Session session) {

        synchronized (liveSessions) {
            liveSessions.put(session.getId(), session);
        }
        System.out.println(connectionRespository);
        try {
            connectionRespository.save(new Connection(session.getId(), "CLIENT"));
        } catch (Exception e) {
            System.out.println(e);
        }

        session.getAsyncRemote().sendText("Connection Id - " + session.getId());

    }

    @OnMessage
    public void onMessage(String message, Session session) throws IOException {

        ObjectMapper mapper = new ObjectMapper();
        Message msg = mapper.readValue(message, Message.class);

        System.out.println(msg);
        synchronized (liveSessions) {
            liveSessions.get(msg.to).getAsyncRemote().sendText(msg.message);
        }
    }

    @OnClose
    public void onClose(Session session) {
        connectionRespository.delete(new Connection(session.getId(), "CLIENT"));
    }
}
