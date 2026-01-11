package com.example.rendezvousmanager.services;

import com.example.rendezvousmanager.dao.UtilisateurDAO;
import com.example.rendezvousmanager.models.Utilisateur;
import java.util.List;

public class UtilisateurService {
    private final UtilisateurDAO dao = com.example.rendezvousmanager.dao.DataManager.getUtilisateurDAO();

    public List<Utilisateur> getAllUtilisateurs() { return dao.getAll(); }

    public void addUtilisateur(Utilisateur u) { dao.add(u); }

    public void updateUtilisateur(Utilisateur u) { dao.update(u); }

    public void deleteUtilisateur(Utilisateur u) { dao.delete(u); }
}
