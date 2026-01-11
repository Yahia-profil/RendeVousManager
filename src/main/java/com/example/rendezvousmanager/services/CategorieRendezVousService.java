package com.example.rendezvousmanager.services;

import com.example.rendezvousmanager.dao.CategorieRendezVousDAO;
import com.example.rendezvousmanager.models.CategorieRendezVous;
import java.util.List;

public class CategorieRendezVousService {
    private final CategorieRendezVousDAO dao = com.example.rendezvousmanager.dao.DataManager.getCategorieDAO();

    public List<CategorieRendezVous> getAllCategories() { return dao.getAll(); }

    public void addCategorie(CategorieRendezVous c) { dao.add(c); }

    public void updateCategorie(CategorieRendezVous c) { dao.update(c); }

    public void deleteCategorie(CategorieRendezVous c) { dao.delete(c); }
}
