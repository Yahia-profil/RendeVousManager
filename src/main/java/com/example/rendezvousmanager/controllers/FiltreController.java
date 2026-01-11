package com.example.rendezvousmanager.controllers;

import com.example.rendezvousmanager.SceneManager;
import com.example.rendezvousmanager.models.RendezVous;
import com.example.rendezvousmanager.services.RendezVousService;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class FiltreController {

    @FXML
    private TextField clientField;
    @FXML
    private TextField lieuField;
    @FXML
    private DatePicker datePicker;
    @FXML
    private TableView<RendezVous> filteredTable;
    @FXML
    private TableColumn<RendezVous, Integer> idColumn;
    @FXML
    private TableColumn<RendezVous, String> clientColumn;
    @FXML
    private TableColumn<RendezVous, String> lieuColumn;
    @FXML
    private TableColumn<RendezVous, String> dateColumn;

    private final RendezVousService service = new RendezVousService();

    @FXML
    public void initialize() {
        System.out.println("FiltreController initialisé");
        
        if (idColumn != null) {
            idColumn.setCellValueFactory(data -> data.getValue().idProperty().asObject());
        }
        if (clientColumn != null) {
            clientColumn.setCellValueFactory(data -> {
                if (data.getValue().getClient() != null) {
                    return new javafx.beans.property.SimpleStringProperty(
                        data.getValue().getClient().getPrenom() + " " + data.getValue().getClient().getNom()
                    );
                }
                return new javafx.beans.property.SimpleStringProperty("");
            });
        }
        if (lieuColumn != null) {
            lieuColumn.setCellValueFactory(data -> {
                if (data.getValue().getLieu() != null) {
                    return new javafx.beans.property.SimpleStringProperty(data.getValue().getLieu().getNom());
                }
                return new javafx.beans.property.SimpleStringProperty("");
            });
        }
        if (dateColumn != null) {
            dateColumn.setCellValueFactory(data -> {
                if (data.getValue().getDate() != null) {
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
                    return new javafx.beans.property.SimpleStringProperty(
                        data.getValue().getDate().format(formatter)
                    );
                }
                return new javafx.beans.property.SimpleStringProperty("");
            });
        }
        
        refreshTable();
    }

    @FXML
    private void applyFilter() {
        String clientFilter = clientField.getText().toLowerCase().trim();
        String lieuFilter = lieuField.getText().toLowerCase().trim();
        LocalDate dateFilter = datePicker.getValue();
        
        filteredTable.getItems().clear();
        
        // Si aucun champ n'est rempli, afficher tous les rendez-vous
        if (clientFilter.isEmpty() && lieuFilter.isEmpty() && dateFilter == null) {
            refreshTable();
            return;
        }
        
        for (RendezVous rdv : service.getAllRendezVous()) {
            boolean match = true;
            
            // Filtrer par client (seulement si le champ est rempli)
            if (!clientFilter.isEmpty()) {
                if (rdv.getClient() == null || 
                    !(rdv.getClient().getPrenom() + " " + rdv.getClient().getNom()).toLowerCase().contains(clientFilter)) {
                    match = false;
                }
            }
            
            // Filtrer par lieu (seulement si le champ est rempli)
            if (!lieuFilter.isEmpty()) {
                if (rdv.getLieu() == null || 
                    !rdv.getLieu().getNom().toLowerCase().contains(lieuFilter)) {
                    match = false;
                }
            }
            
            // Filtrer par date (seulement si le champ est rempli)
            if (dateFilter != null) {
                if (rdv.getDate() == null || 
                    !rdv.getDate().toLocalDate().equals(dateFilter)) {
                    match = false;
                }
            }
            
            if (match) {
                filteredTable.getItems().add(rdv);
            }
        }
    }

    @FXML
    private void resetFilter() {
        clientField.clear();
        lieuField.clear();
        if (datePicker != null) {
            datePicker.setValue(null);
        }
        refreshTable();
    }

    private void refreshTable() {
        filteredTable.getItems().setAll(service.getAllRendezVous());
    }

    @FXML
    private void goHome() {
        SceneManager.navigateTo("home-view");
    }
}
