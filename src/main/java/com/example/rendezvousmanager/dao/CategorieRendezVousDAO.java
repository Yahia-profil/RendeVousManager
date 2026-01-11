package com.example.rendezvousmanager.dao;

import com.example.rendezvousmanager.models.CategorieRendezVous;
import java.util.ArrayList;
import java.util.List;

public class CategorieRendezVousDAO {
    private final List<CategorieRendezVous> categories = new ArrayList<>();

    public List<CategorieRendezVous> getAll() { return categories; }

    public void add(CategorieRendezVous c) {
        c.setId(categories.size() + 1);
        categories.add(c);
    }

    public void update(CategorieRendezVous c) {
        for (int i = 0; i < categories.size(); i++) {
            if (categories.get(i).getId() == c.getId()) {
                categories.set(i, c);
                break;
            }
        }
    }

    public void delete(CategorieRendezVous c) { categories.remove(c); }
}
