package com.example.rendezvousmanager.dao;

import com.example.rendezvousmanager.models.Utilisateur;
import java.util.ArrayList;
import java.util.List;

public class UtilisateurDAO {
    private final List<Utilisateur> utilisateurs = new ArrayList<>();

    public List<Utilisateur> getAll() { return utilisateurs; }

    public void add(Utilisateur u) {
        u.setId(utilisateurs.size() + 1);
        utilisateurs.add(u);
    }

    public void update(Utilisateur u) {
        for (int i = 0; i < utilisateurs.size(); i++) {
            if (utilisateurs.get(i).getId() == u.getId()) {
                utilisateurs.set(i, u);
                break;
            }
        }
    }

    public void delete(Utilisateur u) { utilisateurs.remove(u); }
}
