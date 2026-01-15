package com.example.rendezvousmanager.services;

import com.example.rendezvousmanager.jdbc.ClientJDBC;
import com.example.rendezvousmanager.models.Client;
import java.util.List;

public class ClientService {
    private final ClientJDBC jdbc = com.example.rendezvousmanager.dao.DataManager.getClientJDBC();

    public List<Client> getAllClients() { return jdbc.getAll(); }

    public void addClient(Client c) { jdbc.add(c); }

    public void updateClient(Client c) { jdbc.update(c); }

    public void deleteClient(Client c) { jdbc.delete(c); }
}
