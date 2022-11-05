package com.websole.socket.database.connections;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Connection {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String ConnectionId;

    @Enumerated(EnumType.STRING)
    private Type type;

    public Connection() {
    }

    public Connection(String id, String type) {
        this.setConnectionId(id);
        this.setType(type);
    }

    public String getConnectionId() {
        return ConnectionId;
    }

    public void setConnectionId(String id) {
        this.ConnectionId = id;
    }

    public String getType() {
        return type.toString();
    }

    public void setType(String type) {
        this.type = Type.valueOf(type);
    }
}