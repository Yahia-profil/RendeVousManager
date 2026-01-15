package com.example.rendezvousmanager.controllers;

import com.example.rendezvousmanager.models.Lieu;
import com.example.rendezvousmanager.services.LieuService;
import com.example.rendezvousmanager.SceneManager;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class LieuController {

    @FXML
    private TableView<Lieu> lieuTable;
    @FXML
    private TableColumn<Lieu, Integer> idColumn;
    @FXML
    private TableColumn<Lieu, String> nomColumn;
    @FXML
    private TableColumn<Lieu, String> adresseColumn;

    @FXML
    private TextField nomField;
    @FXML
    private TextField adresseField;

    private final LieuService service = new LieuService();

    @FXML
    public void initialize() {
        System.out.println("LieuController initialisé");

        idColumn.setCellValueFactory(data -> data.getValue().idProperty().asObject());
        nomColumn.setCellValueFactory(data -> data.getValue().nomProperty());
        if (adresseColumn != null) {
            adresseColumn.setCellValueFactory(data -> data.getValue().adresseProperty());
        }

        refreshTable();
        
        // Remplir les champs quand on sélectionne une ligne
        lieuTable.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                nomField.setText(newVal.getNom());
                if (adresseField != null) {
                    adresseField.setText(newVal.getAdresse());
                }
            }
        });
    }

    private void refreshTable() {
        lieuTable.getItems().setAll(service.getAllLieux());
    }

    @FXML
    private void addLieu() {
        String nom = nomField.getText();
        String adresse = adresseField != null ? adresseField.getText() : "";
        service.addLieu(new Lieu(nom, adresse));
        refreshTable();
        nomField.clear();
        if (adresseField != null) adresseField.clear();
    }

    @FXML
    private void updateLieu() {
        Lieu selected = lieuTable.getSelectionModel().getSelectedItem();
        if (selected != null) {
            selected.setNom(nomField.getText());
            if (adresseField != null) {
                selected.setAdresse(adresseField.getText());
            }
            service.updateLieu(selected);
            refreshTable();
        }
    }

    @FXML
    private void deleteLieu() {
        Lieu selected = lieuTable.getSelectionModel().getSelectedItem();
        if (selected != null) {
            service.deleteLieu(selected);
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
