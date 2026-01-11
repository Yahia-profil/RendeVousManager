package com.example.rendezvousmanager.dao;

public class DataManager {
    // Instances partagées des DAO
    private static final ClientDAO clientDAO = new ClientDAO();
    private static final UtilisateurDAO utilisateurDAO = new UtilisateurDAO();
    private static final CategorieRendezVousDAO categorieDAO = new CategorieRendezVousDAO();
    private static final LieuDAO lieuDAO = new LieuDAO();
    private static final RendezVousDAO rendezVousDAO = new RendezVousDAO();
    
    public static ClientDAO getClientDAO() { return clientDAO; }
    public static UtilisateurDAO getUtilisateurDAO() { return utilisateurDAO; }
    public static CategorieRendezVousDAO getCategorieDAO() { return categorieDAO; }
    public static LieuDAO getLieuDAO() { return lieuDAO; }
    public static RendezVousDAO getRendezVousDAO() { return rendezVousDAO; }
}
