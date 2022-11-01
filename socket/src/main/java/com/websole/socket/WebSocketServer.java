package com.websole.socket;

import org.glassfish.tyrus.server.Server;
import javax.websocket.DeploymentException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class WebSocketServer {
    public static void main(String[] args) {
        SpringApplication.run(WebSocketServer.class, args);
        Server server = new Server("localhost", 4000, "/ws", WebSocketServerEndpoint.class);
        try {
            server.start();
            while (true)
                ;
        } catch (DeploymentException e) {
            e.printStackTrace();
        }
    }
}