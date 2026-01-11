package com.example.rendezvousmanager;

import com.example.rendezvousmanager.services.*;
import com.example.rendezvousmanager.models.*;
import java.time.LocalDateTime;

public class DataInitializer {
    
    private static boolean initialized = false;
    
    public static void initialize() {
        if (initialized) return;
        
        ClientService clientService = new ClientService();
        UtilisateurService utilisateurService = new UtilisateurService();
        CategorieRendezVousService categorieService = new CategorieRendezVousService();
        LieuService lieuService = new LieuService();
        RendezVousService rendezVousService = new RendezVousService();
        
        // Initialiser les Clients
        clientService.addClient(new Client("Dupont", "Jean"));
        clientService.addClient(new Client("Martin", "Marie"));
        clientService.addClient(new Client("Bernard", "Pierre"));
        clientService.addClient(new Client("Dubois", "Sophie"));
        clientService.addClient(new Client("Moreau", "Luc"));
        clientService.addClient(new Client("Petit", "Claire"));
        
        // Initialiser les Utilisateurs
        utilisateurService.addUtilisateur(new Utilisateur("Admin", "Système", "admin@rendezvous.com"));
        utilisateurService.addUtilisateur(new Utilisateur("Alice", "Durand", "alice.durand@rendezvous.com"));
        utilisateurService.addUtilisateur(new Utilisateur("Bob", "Lefebvre", "bob.lefebvre@rendezvous.com"));
        utilisateurService.addUtilisateur(new Utilisateur("Claire", "Moreau", "claire.moreau@rendezvous.com"));
        
        // Initialiser les Catégories
        categorieService.addCategorie(new CategorieRendezVous("Consultation", "Consultation médicale générale"));
        categorieService.addCategorie(new CategorieRendezVous("Suivi", "Suivi de traitement"));
        categorieService.addCategorie(new CategorieRendezVous("Urgence", "Rendez-vous urgent"));
        categorieService.addCategorie(new CategorieRendezVous("Contrôle", "Contrôle de routine"));
        categorieService.addCategorie(new CategorieRendezVous("Spécialiste", "Consultation avec spécialiste"));
        
        // Initialiser les Lieux
        lieuService.addLieu(new Lieu("Cabinet Principal", "123 Rue de la Santé, 75001 Paris"));
        lieuService.addLieu(new Lieu("Cabinet Secondaire", "45 Avenue des Médecins, 69000 Lyon"));
        lieuService.addLieu(new Lieu("Clinique", "78 Boulevard Médical, 13000 Marseille"));
        lieuService.addLieu(new Lieu("Centre de Santé", "12 Place de la Santé, 33000 Bordeaux"));
        lieuService.addLieu(new Lieu("Cabinet Urgence", "56 Rue de l'Urgence, 31000 Toulouse"));
        
        // Initialiser les Rendez-vous
        java.util.List<Client> clients = clientService.getAllClients();
        java.util.List<CategorieRendezVous> categories = categorieService.getAllCategories();
        java.util.List<Lieu> lieux = lieuService.getAllLieux();
        
        if (!clients.isEmpty() && !categories.isEmpty() && !lieux.isEmpty()) {
            rendezVousService.addRendezVous(new RendezVous(1, 
                clients.get(0), 
                categories.get(0), 
                lieux.get(0), 
                LocalDateTime.now().plusDays(1).withHour(10).withMinute(0)));
            rendezVousService.addRendezVous(new RendezVous(2, 
                clients.get(1), 
                categories.get(1), 
                lieux.get(1), 
                LocalDateTime.now().plusDays(2).withHour(14).withMinute(30)));
            rendezVousService.addRendezVous(new RendezVous(3, 
                clients.get(2), 
                categories.get(2), 
                lieux.get(0), 
                LocalDateTime.now().plusDays(3).withHour(9).withMinute(15)));
            rendezVousService.addRendezVous(new RendezVous(4, 
                clients.get(3), 
                categories.get(0), 
                lieux.get(2), 
                LocalDateTime.now().plusDays(5).withHour(11).withMinute(0)));
        }
        
        initialized = true;
    }
}
