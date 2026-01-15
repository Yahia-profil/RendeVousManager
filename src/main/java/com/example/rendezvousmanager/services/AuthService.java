package com.example.rendezvousmanager.services;

import com.example.rendezvousmanager.models.Utilisateur;
import com.example.rendezvousmanager.models.Session;
import java.util.List;

public class AuthService {
    private final UtilisateurService utilisateurService = new UtilisateurService();
    
    public boolean login(String email, String motDePasse) {
        List<Utilisateur> utilisateurs = utilisateurService.getAllUtilisateurs();
        
        for (Utilisateur user : utilisateurs) {
            if (user.getEmail().equalsIgnoreCase(email)) {
                // Pour la démo: mot de passe = email (à améliorer avec hash)
                if (email.equals(motDePasse)) {
                    Session.login(user);
                    return true;
                }
            }
        }
        return false;
    }
    
    public boolean signup(String nom, String prenom, String email, String motDePasse) {
        List<Utilisateur> utilisateurs = utilisateurService.getAllUtilisateurs();
        
        // Vérifier si l'email existe déjà
        for (Utilisateur user : utilisateurs) {
            if (user.getEmail().equalsIgnoreCase(email)) {
                return false; // Email déjà utilisé
            }
        }
        
        // Créer le nouvel utilisateur
        Utilisateur nouvelUtilisateur = new Utilisateur(nom, prenom, email);
        utilisateurService.addUtilisateur(nouvelUtilisateur);
        
        // Connecter automatiquement
        Session.login(nouvelUtilisateur);
        return true;
    }
    
    public void logout() {
        Session.logout();
    }
}
