package com.example.rendezvousmanager.services;

import com.example.rendezvousmanager.dao.ClientDAO;
import com.example.rendezvousmanager.models.Client;
import java.util.List;

public class ClientService {
    private final ClientDAO dao = com.example.rendezvousmanager.dao.DataManager.getClientDAO();

    public List<Client> getAllClients() { return dao.getAll(); }

    public void addClient(Client c) { dao.add(c); }

    public void updateClient(Client c) { dao.update(c); }

    public void deleteClient(Client c) { dao.delete(c); }
}
