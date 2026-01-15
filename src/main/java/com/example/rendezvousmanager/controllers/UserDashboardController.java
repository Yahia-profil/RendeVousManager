package com.example.rendezvousmanager.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import com.example.rendezvousmanager.SceneManager;
import com.example.rendezvousmanager.models.Session;
import com.example.rendezvousmanager.services.AuthService;

public class UserDashboardController {
    
    @FXML private Label welcomeLabel;
    @FXML private Button logoutButton;
    
    private final AuthService authService = new AuthService();
    
    @FXML
    public void initialize() {
        // Afficher le nom de l'utilisateur connecté
        welcomeLabel.setText("Bienvenue " + Session.getDisplayName());
    }
    
    @FXML
    private void handleLogout() {
        authService.logout();
        SceneManager.navigateTo("login-view");
    }
    
    @FXML
    private void goToClients() {
        SceneManager.navigateTo("client-view");
    }
    
    @FXML
    private void goToRendezVous() {
        SceneManager.navigateTo("rendezvous-view");
    }
    
    @FXML
    private void goToFiltres() {
        SceneManager.navigateTo("filtre-view");
    }
}
