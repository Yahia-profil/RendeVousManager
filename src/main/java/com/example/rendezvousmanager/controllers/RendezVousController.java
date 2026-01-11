package com.example.rendezvousmanager.controllers;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import com.example.rendezvousmanager.models.RendezVous;
import com.example.rendezvousmanager.models.Client;
import com.example.rendezvousmanager.models.Lieu;
import com.example.rendezvousmanager.services.RendezVousService;
import com.example.rendezvousmanager.services.ClientService;
import com.example.rendezvousmanager.services.LieuService;
import com.example.rendezvousmanager.SceneManager;
import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;

public class RendezVousController {
    private final RendezVousService service = new RendezVousService();
    private final ClientService clientService = new ClientService();
    private final LieuService lieuService = new LieuService();
    
    @FXML private TableView<RendezVous> tableRdv;
    @FXML private TableColumn<RendezVous, String> colClient, colLieu, colDate;
    @FXML private ComboBox<Client> clientCombo;
    @FXML private ComboBox<Lieu> lieuCombo;
    @FXML private DatePicker datePicker;

    @FXML public void initialize() {
        // Configuration des colonnes
        colClient.setCellValueFactory(data -> {
            if (data.getValue().getClient() != null) {
                return new javafx.beans.property.SimpleStringProperty(
                    data.getValue().getClient().getPrenom() + " " + data.getValue().getClient().getNom()
                );
            }
            return new javafx.beans.property.SimpleStringProperty("");
        });
        
        colLieu.setCellValueFactory(data -> {
            if (data.getValue().getLieu() != null) {
                return new javafx.beans.property.SimpleStringProperty(data.getValue().getLieu().getNom());
            }
            return new javafx.beans.property.SimpleStringProperty("");
        });
        
        colDate.setCellValueFactory(data -> {
            if (data.getValue().getDate() != null) {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
                return new javafx.beans.property.SimpleStringProperty(
                    data.getValue().getDate().format(formatter)
                );
            }
            return new javafx.beans.property.SimpleStringProperty("");
        });
        
        // Configuration des ComboBox pour afficher les noms
        clientCombo.getItems().setAll(clientService.getAllClients());
        clientCombo.setCellFactory(param -> new javafx.scene.control.ListCell<Client>() {
            @Override
            protected void updateItem(Client client, boolean empty) {
                super.updateItem(client, empty);
                if (empty || client == null) {
                    setText(null);
                } else {
                    setText(client.getPrenom() + " " + client.getNom());
                }
            }
        });
        clientCombo.setButtonCell(new javafx.scene.control.ListCell<Client>() {
            @Override
            protected void updateItem(Client client, boolean empty) {
                super.updateItem(client, empty);
                if (empty || client == null) {
                    setText(null);
                } else {
                    setText(client.getPrenom() + " " + client.getNom());
                }
            }
        });
        
        lieuCombo.getItems().setAll(lieuService.getAllLieux());
        lieuCombo.setCellFactory(param -> new javafx.scene.control.ListCell<Lieu>() {
            @Override
            protected void updateItem(Lieu lieu, boolean empty) {
                super.updateItem(lieu, empty);
                if (empty || lieu == null) {
                    setText(null);
                } else {
                    setText(lieu.getNom());
                }
            }
        });
        lieuCombo.setButtonCell(new javafx.scene.control.ListCell<Lieu>() {
            @Override
            protected void updateItem(Lieu lieu, boolean empty) {
                super.updateItem(lieu, empty);
                if (empty || lieu == null) {
                    setText(null);
                } else {
                    setText(lieu.getNom());
                }
            }
        });
        
        refreshTable();
    }

    private void refreshTable() {
        tableRdv.getItems().setAll(service.getAllRendezVous());
    }

    @FXML
    private void addRendezVous() {
        try {
            Client selectedClient = clientCombo.getValue();
            Lieu selectedLieu = lieuCombo.getValue();
            LocalDateTime selectedDate = datePicker.getValue() != null ? 
                datePicker.getValue().atStartOfDay() : null;
            
            if (selectedClient != null && selectedLieu != null && selectedDate != null) {
                RendezVous newRdv = new RendezVous(0, selectedClient, null, selectedLieu, selectedDate);
                service.addRendezVous(newRdv);
                refreshTable();
                clearFields();
            }
        } catch (Exception e) {
            System.err.println("Erreur lors de l'ajout du rendez-vous: " + e.getMessage());
        }
    }

    @FXML
    private void updateRendezVous() {
        try {
            RendezVous selected = tableRdv.getSelectionModel().getSelectedItem();
            if (selected != null) {
                Client selectedClient = clientCombo.getValue();
                Lieu selectedLieu = lieuCombo.getValue();
                LocalDateTime selectedDate = datePicker.getValue() != null ? 
                    datePicker.getValue().atStartOfDay() : null;
                
                if (selectedClient != null && selectedLieu != null && selectedDate != null) {
                    selected.setClient(selectedClient);
                    selected.setLieu(selectedLieu);
                    selected.setDate(selectedDate);
                    service.updateRendezVous(selected);
                    refreshTable();
                    clearFields();
                }
            }
        } catch (Exception e) {
            System.err.println("Erreur lors de la modification du rendez-vous: " + e.getMessage());
        }
    }

    @FXML
    private void deleteRendezVous() {
        try {
            RendezVous selected = tableRdv.getSelectionModel().getSelectedItem();
            if (selected != null) {
                service.deleteRendezVous(selected);
                refreshTable();
                clearFields();
            }
        } catch (Exception e) {
            System.err.println("Erreur lors de la suppression du rendez-vous: " + e.getMessage());
        }
    }

    private void clearFields() {
        clientCombo.setValue(null);
        lieuCombo.setValue(null);
        datePicker.setValue(null);
    }

    @FXML
    private void goHome() {
        SceneManager.navigateTo("home-view");
    }
}
