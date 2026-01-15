package com.example.rendezvousmanager.controllers;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import com.example.rendezvousmanager.SceneManager;
import com.example.rendezvousmanager.services.ClientService;
import com.example.rendezvousmanager.services.UtilisateurService;
import com.example.rendezvousmanager.services.LieuService;
import com.example.rendezvousmanager.services.RendezVousService;
import com.example.rendezvousmanager.models.Client;
import com.example.rendezvousmanager.models.Utilisateur;
import com.example.rendezvousmanager.models.Lieu;
import com.example.rendezvousmanager.models.RendezVous;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class StatistiquesController {
    @FXML private Label totalClientsLabel;
    @FXML private Label totalUsersLabel;
    @FXML private Label totalLieuxLabel;
    @FXML private Label totalRdvLabel;
    @FXML private VBox topLieuxContainer;
    @FXML private VBox topClientsContainer;
    
    private final ClientService clientService = new ClientService();
    private final UtilisateurService utilisateurService = new UtilisateurService();
    private final LieuService lieuService = new LieuService();
    private final RendezVousService rendezVousService = new RendezVousService();

    @FXML
    public void initialize() {
        loadStatistics();
    }

    private void loadStatistics() {
        // Charger les statistiques générales
        loadGeneralStatistics();
        
        // Charger les top lieux
        loadTopLieux();
        
        // Charger les top clients
        loadTopClients();
    }

    private void loadGeneralStatistics() {
        try {
            List<Client> clients = clientService.getAllClients();
            List<Utilisateur> utilisateurs = utilisateurService.getAllUtilisateurs();
            List<Lieu> lieux = lieuService.getAllLieux();
            List<RendezVous> rendezVous = rendezVousService.getAllRendezVous();

            totalClientsLabel.setText(String.valueOf(clients.size()));
            totalUsersLabel.setText(String.valueOf(utilisateurs.size()));
            totalLieuxLabel.setText(String.valueOf(lieux.size()));
            totalRdvLabel.setText(String.valueOf(rendezVous.size()));
        } catch (Exception e) {
            System.err.println("Erreur lors du chargement des statistiques générales: " + e.getMessage());
            totalClientsLabel.setText("Erreur");
            totalUsersLabel.setText("Erreur");
            totalLieuxLabel.setText("Erreur");
            totalRdvLabel.setText("Erreur");
        }
    }

    private void loadTopLieux() {
        try {
            List<RendezVous> rendezVous = rendezVousService.getAllRendezVous();
            
            // Compter les rendez-vous par lieu
            Map<String, Long> lieuCounts = rendezVous.stream()
                .filter(rd -> rd.getLieu() != null)
                .collect(Collectors.groupingBy(
                    rd -> rd.getLieu().getNom(),
                    Collectors.counting()
                ));

            // Trier et prendre les top 5
            List<Map.Entry<String, Long>> topLieux = lieuCounts.entrySet().stream()
                .sorted(Map.Entry.<String, Long>comparingByValue().reversed())
                .limit(5)
                .collect(Collectors.toList());

            topLieuxContainer.getChildren().clear();
            
            if (topLieux.isEmpty()) {
                Label noDataLabel = new Label("Aucun rendez-vous enregistré");
                noDataLabel.setStyle("-fx-text-fill: #999; -fx-font-style: italic;");
                topLieuxContainer.getChildren().add(noDataLabel);
            } else {
                int rank = 1;
                for (Map.Entry<String, Long> entry : topLieux) {
                    HBox itemBox = createTopItemBox(rank, entry.getKey(), entry.getValue() + " rendez-vous");
                    topLieuxContainer.getChildren().add(itemBox);
                    rank++;
                }
            }
        } catch (Exception e) {
            System.err.println("Erreur lors du chargement des top lieux: " + e.getMessage());
            Label errorLabel = new Label("Erreur de chargement");
            errorLabel.setStyle("-fx-text-fill: #f44336;");
            topLieuxContainer.getChildren().add(errorLabel);
        }
    }

    private void loadTopClients() {
        try {
            List<RendezVous> rendezVous = rendezVousService.getAllRendezVous();
            
            // Compter les rendez-vous par client
            Map<String, Long> clientCounts = rendezVous.stream()
                .filter(rd -> rd.getClient() != null)
                .collect(Collectors.groupingBy(
                    rd -> rd.getClient().getPrenom() + " " + rd.getClient().getNom(),
                    Collectors.counting()
                ));

            // Trier et prendre les top 5
            List<Map.Entry<String, Long>> topClients = clientCounts.entrySet().stream()
                .sorted(Map.Entry.<String, Long>comparingByValue().reversed())
                .limit(5)
                .collect(Collectors.toList());

            topClientsContainer.getChildren().clear();
            
            if (topClients.isEmpty()) {
                Label noDataLabel = new Label("Aucun rendez-vous enregistré");
                noDataLabel.setStyle("-fx-text-fill: #999; -fx-font-style: italic;");
                topClientsContainer.getChildren().add(noDataLabel);
            } else {
                int rank = 1;
                for (Map.Entry<String, Long> entry : topClients) {
                    HBox itemBox = createTopItemBox(rank, entry.getKey(), entry.getValue() + " rendez-vous");
                    topClientsContainer.getChildren().add(itemBox);
                    rank++;
                }
            }
        } catch (Exception e) {
            System.err.println("Erreur lors du chargement des top clients: " + e.getMessage());
            Label errorLabel = new Label("Erreur de chargement");
            errorLabel.setStyle("-fx-text-fill: #f44336;");
            topClientsContainer.getChildren().add(errorLabel);
        }
    }

    private HBox createTopItemBox(int rank, String name, String count) {
        HBox box = new HBox(10);
        box.setStyle("-fx-background-color: white; -fx-padding: 10px; -fx-background-radius: 5px; -fx-alignment: center-left;");
        
        String medal = "";
        String color = "#666";
        if (rank == 1) { medal = "🥇"; color = "#FFD700"; }
        else if (rank == 2) { medal = "🥈"; color = "#C0C0C0"; }
        else if (rank == 3) { medal = "🥉"; color = "#CD7F32"; }
        else { medal = rank + "."; }
        
        Label rankLabel = new Label(medal);
        rankLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-min-width: 30px;");
        
        Label nameLabel = new Label(name);
        nameLabel.setStyle("-fx-font-weight: 500; -fx-text-fill: #333;");
        
        Label countLabel = new Label(count);
        countLabel.setStyle("-fx-text-fill: " + color + "; -fx-font-weight: bold;");
        
        box.getChildren().addAll(rankLabel, nameLabel, countLabel);
        return box;
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
