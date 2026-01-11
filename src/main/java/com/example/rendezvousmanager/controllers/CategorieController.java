package com.example.rendezvousmanager.controllers;

import com.example.rendezvousmanager.models.CategorieRendezVous;
import com.example.rendezvousmanager.services.CategorieRendezVousService;
import com.example.rendezvousmanager.SceneManager;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class CategorieController {

    @FXML
    private TableView<CategorieRendezVous> categorieTable;
    @FXML
    private TableColumn<CategorieRendezVous, Integer> idColumn;
    @FXML
    private TableColumn<CategorieRendezVous, String> nomColumn;
    @FXML
    private TableColumn<CategorieRendezVous, String> descColumn;

    @FXML
    private TextField nomField;
    @FXML
    private TextField descField;

    private final CategorieRendezVousService service = new CategorieRendezVousService();

    @FXML
    public void initialize() {
        System.out.println("CategorieController initialisé");

        idColumn.setCellValueFactory(data -> data.getValue().idProperty().asObject());
        nomColumn.setCellValueFactory(data -> data.getValue().nomProperty());
        if (descColumn != null) {
            descColumn.setCellValueFactory(data -> data.getValue().descriptionProperty());
        }

        refreshTable();
        
        // Remplir les champs quand on sélectionne une ligne
        categorieTable.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                nomField.setText(newVal.getNom());
                if (descField != null) {
                    descField.setText(newVal.getDescription());
                }
            }
        });
    }

    private void refreshTable() {
        categorieTable.getItems().setAll(service.getAllCategories());
    }

    @FXML
    private void addCategorie() {
        String nom = nomField.getText();
        String desc = descField != null ? descField.getText() : "";
        service.addCategorie(new CategorieRendezVous(nom, desc));
        refreshTable();
        nomField.clear();
        if (descField != null) descField.clear();
    }

    @FXML
    private void updateCategorie() {
        CategorieRendezVous selected = categorieTable.getSelectionModel().getSelectedItem();
        if (selected != null) {
            selected.setNom(nomField.getText());
            if (descField != null) {
                selected.setDescription(descField.getText());
            }
            service.updateCategorie(selected);
            refreshTable();
        }
    }

    @FXML
    private void deleteCategorie() {
        CategorieRendezVous selected = categorieTable.getSelectionModel().getSelectedItem();
        if (selected != null) {
            service.deleteCategorie(selected);
            refreshTable();
        }
    }

    @FXML
    private void goHome() {
        SceneManager.navigateTo("home-view");
    }
}
