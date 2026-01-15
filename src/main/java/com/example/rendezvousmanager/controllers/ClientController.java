package com.example.rendezvousmanager.controllers;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import com.example.rendezvousmanager.models.Client;
import com.example.rendezvousmanager.services.ClientService;
import com.example.rendezvousmanager.SceneManager;

public class ClientController {
    private final ClientService service = new ClientService();
    @FXML private TableView<Client> tableClients;
    @FXML private TableColumn<Client, Integer> colId;
    @FXML private TableColumn<Client, String> colNom, colPrenom;
    @FXML private TextField nomField;
    @FXML private TextField prenomField;

    @FXML public void initialize() {
        colId.setCellValueFactory(data -> data.getValue().idProperty().asObject());
        colNom.setCellValueFactory(data -> data.getValue().nomProperty());
        colPrenom.setCellValueFactory(data -> data.getValue().prenomProperty());
        refreshTable();
        
        // Remplir les champs quand on sélectionne une ligne
        tableClients.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                nomField.setText(newVal.getNom());
                prenomField.setText(newVal.getPrenom());
            }
        });
    }

    private void refreshTable() {
        tableClients.getItems().setAll(service.getAllClients());
    }

    @FXML
    private void addClient() {
        String nom = nomField.getText();
        String prenom = prenomField.getText();
        if (!nom.isEmpty() && !prenom.isEmpty()) {
            service.addClient(new Client(nom, prenom));
            refreshTable();
            nomField.clear();
            prenomField.clear();
        }
    }

    @FXML
    private void updateClient() {
        Client selected = tableClients.getSelectionModel().getSelectedItem();
        if (selected != null) {
            selected.setNom(nomField.getText());
            selected.setPrenom(prenomField.getText());
            service.updateClient(selected);
            refreshTable();
        }
    }

    @FXML
    private void deleteClient() {
        Client selected = tableClients.getSelectionModel().getSelectedItem();
        if (selected != null) {
            service.deleteClient(selected);
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
