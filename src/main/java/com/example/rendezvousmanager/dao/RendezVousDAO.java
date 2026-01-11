package com.example.rendezvousmanager.dao;

import com.example.rendezvousmanager.models.RendezVous;
import java.util.ArrayList;
import java.util.List;

public class RendezVousDAO {
    private final List<RendezVous> rendezVousList = new ArrayList<>();

    public List<RendezVous> getAll() { return rendezVousList; }

    public void add(RendezVous r) {
        r.setId(rendezVousList.size() + 1);
        rendezVousList.add(r);
    }

    public void update(RendezVous r) {
        for (int i = 0; i < rendezVousList.size(); i++) {
            if (rendezVousList.get(i).getId() == r.getId()) {
                rendezVousList.set(i, r);
                break;
            }
        }
    }

    public void delete(RendezVous r) { rendezVousList.remove(r); }
}
