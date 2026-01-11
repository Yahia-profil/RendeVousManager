package com.example.rendezvousmanager.dao;

import com.example.rendezvousmanager.models.Client;
import java.util.ArrayList;
import java.util.List;

public class ClientDAO {
    private final List<Client> clients = new ArrayList<>();

    public List<Client> getAll() { return clients; }

    public void add(Client c) {
        c.setId(clients.size() + 1);
        clients.add(c);
    }

    public void update(Client c) {
        for (int i = 0; i < clients.size(); i++) {
            if (clients.get(i).getId() == c.getId()) {
                clients.set(i, c);
                break;
            }
        }
    }

    public void delete(Client c) { clients.remove(c); }
}
