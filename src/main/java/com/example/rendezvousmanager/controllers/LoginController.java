package com.example.rendezvousmanager.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Label;
import javafx.scene.control.Hyperlink;
import com.example.rendezvousmanager.services.AuthService;
import com.example.rendezvousmanager.SceneManager;

public class LoginController {
    
    @FXML private TextField emailField;
    @FXML private PasswordField passwordField;
    @FXML private Label errorLabel;
    @FXML private Hyperlink signupLink;
    
    private final AuthService authService = new AuthService();
    
    @FXML
    public void initialize() {
        // Vider les champs au démarrage
        emailField.clear();
        passwordField.clear();
        errorLabel.setText("");
    }
    
    @FXML
    private void handleLogin() {
        String email = emailField.getText().trim();
        String password = passwordField.getText();
        
        // Validation
        if (email.isEmpty()) {
            errorLabel.setText("L'email est requis");
            return;
        }
        
        if (password.isEmpty()) {
            errorLabel.setText("Le mot de passe est requis");
            return;
        }
        
        // Tentative de connexion
        if (authService.login(email, password)) {
            // Connexion réussie
            if (com.example.rendezvousmanager.models.Session.isAdmin()) {
                SceneManager.navigateTo("admin-dashboard-view");
            } else {
                SceneManager.navigateTo("user-dashboard-view");
            }
        } else {
            errorLabel.setText("Email ou mot de passe incorrect");
        }
    }
    
    @FXML
    private void goToSignup() {
        SceneManager.navigateTo("signup-view");
    }
}
