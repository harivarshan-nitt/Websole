package com.websole.socket.database.mappings;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Mappings {
    static public Map<String, String> mappings = new ConcurrentHashMap<>();

    static public void addEntry(String ClientId, String ExecutorId) {
        synchronized (mappings) {
            mappings.put(ClientId, ExecutorId);
        }
    }

    static public void removeClient(String id) {
        synchronized (mappings) {
            mappings.remove(id);
        }
    }

    static public void removeExecutor(String id) {
        synchronized (mappings) {
            Iterator<Map.Entry<String, String>> iterator = mappings.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry<String, String> entry = iterator.next();
                if (id.equals(entry.getValue())) {
                    iterator.remove();
                }
            }
        }
    }

    static public List<String> getClients(String id) {
        List<String> clients = new ArrayList<String>();
        synchronized (mappings) {
            Iterator<Map.Entry<String, String>> iterator = mappings.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry<String, String> entry = iterator.next();
                if (id.equals(entry.getValue())) {
                    clients.add(entry.getKey());
                }
            }
        }
        return clients;
    }

}
