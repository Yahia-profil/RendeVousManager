package com.example.rendezvousmanager;

import javafx.application.Application;
import javafx.stage.Stage;

public class HelloApplication extends Application {

    @Override
    public void start(Stage stage) {
        try {
            // Initialiser la base de données MySQL
            com.example.rendezvousmanager.database.DatabaseConnection.initializeDatabase();
            
            // Initialiser les données
            DataInitializer.initialize();
            
            // D'abord configurer le stage
            SceneManager.setPrimaryStage(stage);
            stage.setTitle("RendezVous Manager - EMSI Casablanca 2025-2026");
            stage.setMinWidth(800);
            stage.setMinHeight(600);
            
            // Ensuite naviguer
            if (com.example.rendezvousmanager.models.Session.isLoggedIn()) {
                // Rediriger selon le rôle
                if (com.example.rendezvousmanager.models.Session.isAdmin()) {
                    SceneManager.navigateTo("admin-dashboard-view");
                } else {
                    SceneManager.navigateTo("user-dashboard-view");
                }
            } else {
                // Page de login par défaut
                SceneManager.navigateTo("login-view");
            }
            
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Impossible de démarrer l'application !");
        }
    }

    public static void main(String[] args) {
        launch();
    }
}
