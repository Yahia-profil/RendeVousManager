package com.example.rendezvousmanager.controllers;

import com.example.rendezvousmanager.models.Utilisateur;
import com.example.rendezvousmanager.services.UtilisateurService;
import com.example.rendezvousmanager.SceneManager;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class UtilisateurController {

    @FXML
    private TableView<Utilisateur> userTable;
    @FXML
    private TableColumn<Utilisateur, Integer> idColumn;
    @FXML
    private TableColumn<Utilisateur, String> nomColumn;
    @FXML
    private TableColumn<Utilisateur, String> prenomColumn;
    @FXML
    private TableColumn<Utilisateur, String> emailColumn;

    @FXML
    private TextField nomField;
    @FXML
    private TextField prenomField;
    @FXML
    private TextField emailField;

    private final UtilisateurService service = new UtilisateurService();

    @FXML
    public void initialize() {
        System.out.println("UtilisateurController initialisé");

        idColumn.setCellValueFactory(data -> data.getValue().idProperty().asObject());
        nomColumn.setCellValueFactory(data -> data.getValue().nomProperty());
        prenomColumn.setCellValueFactory(data -> data.getValue().prenomProperty());
        if (emailColumn != null) {
            emailColumn.setCellValueFactory(data -> data.getValue().emailProperty());
        }

        refreshTable();
        
        // Remplir les champs quand on sélectionne une ligne
        userTable.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                prenomField.setText(newVal.getPrenom());
                nomField.setText(newVal.getNom());
                if (emailField != null) {
                    emailField.setText(newVal.getEmail());
                }
            }
        });
    }

    private void refreshTable() {
        userTable.getItems().setAll(service.getAllUtilisateurs());
    }

    @FXML
    private void addUtilisateur() {
        String prenom = prenomField.getText();
        String nom = nomField.getText();
        String email = emailField != null ? emailField.getText() : "";
        service.addUtilisateur(new Utilisateur(prenom, nom, email));
        refreshTable();
        nomField.clear();
        prenomField.clear();
        if (emailField != null) emailField.clear();
    }

    @FXML
    private void updateUtilisateur() {
        Utilisateur selected = userTable.getSelectionModel().getSelectedItem();
        if (selected != null) {
            selected.setPrenom(prenomField.getText());
            selected.setNom(nomField.getText());
            if (emailField != null) {
                selected.setEmail(emailField.getText());
            }
            service.updateUtilisateur(selected);
            refreshTable();
        }
    }

    @FXML
    private void deleteUtilisateur() {
        Utilisateur selected = userTable.getSelectionModel().getSelectedItem();
        if (selected != null) {
            service.deleteUtilisateur(selected);
            refreshTable();
        }
    }

    @FXML
    private void goHome() {
        if (com.example.rendezvousmanager.models.Session.isAdmin()) {
            SceneManager.navigateTo("admin-dashboard-view");
        } else {
            SceneManager.navigateTo("user-dashboard-view");
        }
    }
}
