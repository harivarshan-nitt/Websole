package com.websole.socket.database.connections;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Connections {
    static public Map<String, ConnectionType> connections = new ConcurrentHashMap<>();

    static public void addEntry(String id, String type) {
        ConnectionType conectionType = ConnectionType.valueOf(type);
        synchronized (connections) {
            connections.put(id, conectionType);
        }
    }

    static public void removeEntry(String id) {
        synchronized (connections) {
            connections.remove(id);
        }
    }

    static public List<String> getExecutors() {
        List<String> executors = new ArrayList<String>();
        for (Map.Entry<String, ConnectionType> entry : connections.entrySet()) {
            if (entry.getValue() == ConnectionType.valueOf("EXECUTOR")) {
                executors.add(entry.getKey());
            }
        }
        return executors;
    }
}
