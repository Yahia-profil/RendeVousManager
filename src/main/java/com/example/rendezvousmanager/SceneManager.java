package com.example.rendezvousmanager;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class SceneManager {

    private static Stage primaryStage;

    public static void setPrimaryStage(Stage stage) {
        primaryStage = stage;
    }

    public static void navigateTo(String fxmlName) {
        try {
            // On charge le fichier FXML depuis le dossier resources/views
            FXMLLoader loader = new FXMLLoader(SceneManager.class.getResource("/views/" + fxmlName + ".fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("❌ Impossible de charger : " + fxmlName + ".fxml");
        }
    }

    // Méthode utilitaire pour revenir à l'accueil
    public static void goHome() {
        navigateTo("home-view");
    }
}
