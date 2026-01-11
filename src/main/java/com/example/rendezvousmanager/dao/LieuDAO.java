package com.example.rendezvousmanager.dao;

import com.example.rendezvousmanager.models.Lieu;
import java.util.ArrayList;
import java.util.List;

public class LieuDAO {
    private final List<Lieu> lieux = new ArrayList<>();

    public List<Lieu> getAll() { return lieux; }

    public void add(Lieu l) {
        l.setId(lieux.size() + 1);
        lieux.add(l);
    }

    public void update(Lieu l) {
        for (int i = 0; i < lieux.size(); i++) {
            if (lieux.get(i).getId() == l.getId()) {
                lieux.set(i, l);
                break;
            }
        }
    }

    public void delete(Lieu l) { lieux.remove(l); }
}
