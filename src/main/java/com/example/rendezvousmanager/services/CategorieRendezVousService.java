package com.example.rendezvousmanager.services;

import com.example.rendezvousmanager.jdbc.CategorieRendezVousJDBC;
import com.example.rendezvousmanager.models.CategorieRendezVous;
import java.util.List;

public class CategorieRendezVousService {
    private final CategorieRendezVousJDBC jdbc = com.example.rendezvousmanager.dao.DataManager.getCategorieJDBC();

    public List<CategorieRendezVous> getAllCategories() { return jdbc.getAll(); }

    public void addCategorie(CategorieRendezVous c) { jdbc.add(c); }

    public void updateCategorie(CategorieRendezVous c) { jdbc.update(c); }

    public void deleteCategorie(CategorieRendezVous c) { jdbc.delete(c); }
}
