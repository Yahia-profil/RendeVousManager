package com.example.rendezvousmanager.services;

import com.example.rendezvousmanager.jdbc.UtilisateurJDBC;
import com.example.rendezvousmanager.models.Utilisateur;
import java.util.List;

public class UtilisateurService {
    private final UtilisateurJDBC jdbc = com.example.rendezvousmanager.dao.DataManager.getUtilisateurJDBC();

    public List<Utilisateur> getAllUtilisateurs() { return jdbc.getAll(); }

    public void addUtilisateur(Utilisateur u) { jdbc.add(u); }

    public void updateUtilisateur(Utilisateur u) { jdbc.update(u); }

    public void deleteUtilisateur(Utilisateur u) { jdbc.delete(u); }
}
