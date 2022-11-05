package com.websole.socket.database.mappings;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Mapping {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String client;
    private String executor;

    public Mapping() {
    }

    public Mapping(String client, String executor) {
        this.setClient(client);
        this.setExecutor(executor);
    }

    public Mapping(int id, String client, String executor) {
        this.setId(id);
        this.setClient(client);
        this.setExecutor(executor);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }

    public String getExecutor() {
        return executor;
    }

    public void setExecutor(String executor) {
        this.executor = executor;
    }
}