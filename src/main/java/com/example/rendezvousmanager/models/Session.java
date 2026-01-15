package com.example.rendezvousmanager.models;

public class Session {
    private static Utilisateur utilisateurConnecte;
    private static boolean isAdmin = false;
    
    public static void login(Utilisateur utilisateur) {
        utilisateurConnecte = utilisateur;
        isAdmin = "admin".equalsIgnoreCase(utilisateur.getNom()) || 
                 "admin".equalsIgnoreCase(utilisateur.getEmail());
    }
    
    public static void logout() {
        utilisateurConnecte = null;
        isAdmin = false;
    }
    
    public static boolean isLoggedIn() {
        return utilisateurConnecte != null;
    }
    
    public static boolean isAdmin() {
        return isAdmin;
    }
    
    public static Utilisateur getUtilisateurConnecte() {
        return utilisateurConnecte;
    }
    
    public static String getDisplayName() {
        if (utilisateurConnecte != null) {
            return utilisateurConnecte.getPrenom() + " " + utilisateurConnecte.getNom();
        }
        return "Invité";
    }
}
