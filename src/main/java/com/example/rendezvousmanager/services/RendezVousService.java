package com.example.rendezvousmanager.services;

import com.example.rendezvousmanager.dao.RendezVousDAO;
import com.example.rendezvousmanager.models.RendezVous;
import java.util.List;

public class RendezVousService {
    private final RendezVousDAO dao = com.example.rendezvousmanager.dao.DataManager.getRendezVousDAO();

    public List<RendezVous> getAllRendezVous() { return dao.getAll(); }

    public void addRendezVous(RendezVous r) { dao.add(r); }

    public void updateRendezVous(RendezVous r) { dao.update(r); }

    public void deleteRendezVous(RendezVous r) { dao.delete(r); }
}
