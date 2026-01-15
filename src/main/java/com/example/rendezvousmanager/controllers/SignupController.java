package com.example.rendezvousmanager.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Label;
import javafx.scene.control.Hyperlink;
import com.example.rendezvousmanager.services.AuthService;
import com.example.rendezvousmanager.SceneManager;

public class SignupController {
    
    @FXML private TextField nomField;
    @FXML private TextField prenomField;
    @FXML private TextField emailField;
    @FXML private PasswordField passwordField;
    @FXML private PasswordField confirmPasswordField;
    @FXML private Label errorLabel;
    @FXML private Hyperlink loginLink;
    
    private final AuthService authService = new AuthService();
    
    @FXML
    public void initialize() {
        // Vider les champs au démarrage
        nomField.clear();
        prenomField.clear();
        emailField.clear();
        passwordField.clear();
        confirmPasswordField.clear();
        errorLabel.setText("");
    }
    
    @FXML
    private void handleSignup() {
        String nom = nomField.getText().trim();
        String prenom = prenomField.getText().trim();
        String email = emailField.getText().trim();
        String password = passwordField.getText();
        String confirmPassword = confirmPasswordField.getText();
        
        // Validation
        if (nom.isEmpty()) {
            errorLabel.setText("Le nom est requis");
            return;
        }
        
        if (prenom.isEmpty()) {
            errorLabel.setText("Le prénom est requis");
            return;
        }
        
        if (email.isEmpty()) {
            errorLabel.setText("L'email est requis");
            return;
        }
        
        if (!email.contains("@")) {
            errorLabel.setText("Email invalide");
            return;
        }
        
        if (password.isEmpty()) {
            errorLabel.setText("Le mot de passe est requis");
            return;
        }
        
        if (!password.equals(confirmPassword)) {
            errorLabel.setText("Les mots de passe ne correspondent pas");
            return;
        }
        
        if (password.length() < 4) {
            errorLabel.setText("Le mot de passe doit contenir au moins 4 caractères");
            return;
        }
        
        // Tentative d'inscription
        if (authService.signup(nom, prenom, email, password)) {
            // Inscription réussie, redirection selon le rôle
            if (com.example.rendezvousmanager.models.Session.isAdmin()) {
                SceneManager.navigateTo("admin-dashboard-view");
            } else {
                SceneManager.navigateTo("user-dashboard-view");
            }
        } else {
            errorLabel.setText("Cet email est déjà utilisé");
        }
    }
    
    @FXML
    private void goToLogin() {
        SceneManager.navigateTo("login-view");
    }
}
