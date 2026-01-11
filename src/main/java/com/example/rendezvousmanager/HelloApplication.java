package com.example.rendezvousmanager;

import javafx.application.Application;
import javafx.stage.Stage;

public class HelloApplication extends Application {

    @Override
    public void start(Stage stage) {
        try {
            // Initialiser les données
            DataInitializer.initialize();
            
            SceneManager.setPrimaryStage(stage);
            SceneManager.navigateTo("home-view"); // écran d'accueil
            stage.setTitle("RendezVous Manager");
            stage.setMinWidth(800);
            stage.setMinHeight(600);
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
