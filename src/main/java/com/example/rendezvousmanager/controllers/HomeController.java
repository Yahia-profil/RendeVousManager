package com.example.rendezvousmanager.controllers;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import com.example.rendezvousmanager.SceneManager;

public class HomeController {
    @FXML private Button btnClients, btnRendezVous, btnStatistiques;
    @FXML public void goToClients() { SceneManager.navigateTo("client-view"); }
    @FXML public void goToUtilisateurs() { SceneManager.navigateTo("utilisateur-view"); }
    @FXML public void goToCategories() { SceneManager.navigateTo("categorie-view"); }
    @FXML public void goToLieux() { SceneManager.navigateTo("lieu-view"); }
    @FXML public void goToRendezVous() { SceneManager.navigateTo("rendezvous-view"); }
    @FXML public void goToFiltres() { SceneManager.navigateTo("filtre-view"); }
    @FXML public void goToStatistiques() { SceneManager.navigateTo("statistiques-view"); }
}
