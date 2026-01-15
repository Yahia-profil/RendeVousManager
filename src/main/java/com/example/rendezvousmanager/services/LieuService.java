package com.example.rendezvousmanager.services;

import com.example.rendezvousmanager.jdbc.LieuJDBC;
import com.example.rendezvousmanager.models.Lieu;
import java.util.List;

public class LieuService {
    private final LieuJDBC jdbc = com.example.rendezvousmanager.dao.DataManager.getLieuJDBC();

    public List<Lieu> getAllLieux() { return jdbc.getAll(); }

    public void addLieu(Lieu l) { jdbc.add(l); }

    public void updateLieu(Lieu l) { jdbc.update(l); }

    public void deleteLieu(Lieu l) { jdbc.delete(l); }
}
