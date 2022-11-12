package com.websole.socket;

import org.glassfish.tyrus.server.Server;
import javax.websocket.DeploymentException;

import com.websole.socket.endpoints.ClientEndpoint;
import com.websole.socket.endpoints.ExecutorEndpoint;

public class Main {
    public static void main(String[] args) {
        Server clientSocket = new Server("localhost", 4000, "/ws", ClientEndpoint.class);
        Server executorSocket = new Server("localhost", 5000, "/ws", ExecutorEndpoint.class);
        try {
            clientSocket.start();
            executorSocket.start();
            while (true)
                ;
        } catch (DeploymentException e) {
            e.printStackTrace();
        }
    }
}