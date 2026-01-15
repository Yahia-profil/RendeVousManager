package com.example.rendezvousmanager.services;

import com.example.rendezvousmanager.jdbc.RendezVousJDBC;
import com.example.rendezvousmanager.models.RendezVous;
import java.util.List;

public class RendezVousService {
    private final RendezVousJDBC jdbc = com.example.rendezvousmanager.dao.DataManager.getRendezVousJDBC();

    public List<RendezVous> getAllRendezVous() { return jdbc.getAll(); }

    public void addRendezVous(RendezVous r) { jdbc.add(r); }

    public void updateRendezVous(RendezVous r) { jdbc.update(r); }

    public void deleteRendezVous(RendezVous r) { jdbc.delete(r); }
}
