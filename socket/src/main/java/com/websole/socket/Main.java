package com.websole.socket;

import org.glassfish.tyrus.server.Server;
import javax.websocket.DeploymentException;

//import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.websole.socket.endpoints.ClientEndpoint;

@SpringBootApplication
public class Main {
    public static void main(String[] args) {
        // SpringApplication.run(Main.class, args);
        Server server = new Server("localhost", 4000, "/ws", ClientEndpoint.class);
        try {
            server.start();
            while (true)
                ;
        } catch (DeploymentException e) {
            e.printStackTrace();
        }
    }
}