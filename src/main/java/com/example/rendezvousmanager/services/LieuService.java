package com.example.rendezvousmanager.services;

import com.example.rendezvousmanager.dao.LieuDAO;
import com.example.rendezvousmanager.models.Lieu;
import java.util.List;

public class LieuService {
    private final LieuDAO dao = com.example.rendezvousmanager.dao.DataManager.getLieuDAO();

    public List<Lieu> getAllLieux() { return dao.getAll(); }

    public void addLieu(Lieu l) { dao.add(l); }

    public void updateLieu(Lieu l) { dao.update(l); }

    public void deleteLieu(Lieu l) { dao.delete(l); }
}
