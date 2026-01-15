package com.example.rendezvousmanager.dao;

import com.example.rendezvousmanager.jdbc.ClientJDBC;
import com.example.rendezvousmanager.jdbc.UtilisateurJDBC;
import com.example.rendezvousmanager.jdbc.CategorieRendezVousJDBC;
import com.example.rendezvousmanager.jdbc.LieuJDBC;
import com.example.rendezvousmanager.jdbc.RendezVousJDBC;

public class DataManager {
    // Instances partagées des JDBC
    private static final ClientJDBC clientJDBC = new ClientJDBC();
    private static final UtilisateurJDBC utilisateurJDBC = new UtilisateurJDBC();
    private static final CategorieRendezVousJDBC categorieJDBC = new CategorieRendezVousJDBC();
    private static final LieuJDBC lieuJDBC = new LieuJDBC();
    private static final RendezVousJDBC rendezVousJDBC = new RendezVousJDBC();
    
    public static ClientJDBC getClientJDBC() { return clientJDBC; }
    public static UtilisateurJDBC getUtilisateurJDBC() { return utilisateurJDBC; }
    public static CategorieRendezVousJDBC getCategorieJDBC() { return categorieJDBC; }
    public static LieuJDBC getLieuJDBC() { return lieuJDBC; }
    public static RendezVousJDBC getRendezVousJDBC() { return rendezVousJDBC; }
}
