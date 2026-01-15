package com.example.rendezvousmanager.jdbc;

import com.example.rendezvousmanager.models.RendezVous;
import com.example.rendezvousmanager.models.Client;
import com.example.rendezvousmanager.models.CategorieRendezVous;
import com.example.rendezvousmanager.models.Lieu;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class RendezVousJDBC {
    private final ClientJDBC clientJDBC = new ClientJDBC();
    private final CategorieRendezVousJDBC categorieJDBC = new CategorieRendezVousJDBC();
    private final LieuJDBC lieuJDBC = new LieuJDBC();
    
    public List<RendezVous> getAll() {
        List<RendezVous> rendezVousList = new ArrayList<>();
        String sql = "SELECT id, client_id, categorie_id, lieu_id, date FROM rendezvous ORDER BY date";
        
        try (Connection conn = com.example.rendezvousmanager.database.DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                int id = rs.getInt("id");
                int clientId = rs.getInt("client_id");
                int categorieId = rs.getInt("categorie_id");
                int lieuId = rs.getInt("lieu_id");
                LocalDateTime date = rs.getTimestamp("date").toLocalDateTime();
                
                // Récupérer les objets associés
                Client client = clientJDBC.findById(clientId);
                CategorieRendezVous categorie = (categorieId > 0) ? categorieJDBC.findById(categorieId) : null;
                Lieu lieu = lieuJDBC.findById(lieuId);
                
                if (client != null && lieu != null) {
                    rendezVousList.add(new RendezVous(id, client, categorie, lieu, date));
                }
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la lecture des rendez-vous: " + e.getMessage());
        }
        
        return rendezVousList;
    }
    
    public void add(RendezVous rendezVous) {
        String sql = "INSERT INTO rendezvous (client_id, categorie_id, lieu_id, date) VALUES (?, ?, ?, ?)";
        
        try (Connection conn = com.example.rendezvousmanager.database.DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            pstmt.setInt(1, rendezVous.getClient().getId());
            
            if (rendezVous.getCategorie() != null) {
                pstmt.setInt(2, rendezVous.getCategorie().getId());
            } else {
                pstmt.setNull(2, Types.INTEGER);
            }
            
            pstmt.setInt(3, rendezVous.getLieu().getId());
            pstmt.setTimestamp(4, Timestamp.valueOf(rendezVous.getDate()));
            
            pstmt.executeUpdate();
            
            // Récupérer l'ID généré
            try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    rendezVous.setId(generatedKeys.getInt(1));
                }
            }
            
        } catch (SQLException e) {
            System.err.println("Erreur lors de l'ajout du rendez-vous: " + e.getMessage());
        }
    }
    
    public void update(RendezVous rendezVous) {
        String sql = "UPDATE rendezvous SET client_id = ?, categorie_id = ?, lieu_id = ?, date = ? WHERE id = ?";
        
        try (Connection conn = com.example.rendezvousmanager.database.DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, rendezVous.getClient().getId());
            
            if (rendezVous.getCategorie() != null) {
                pstmt.setInt(2, rendezVous.getCategorie().getId());
            } else {
                pstmt.setNull(2, Types.INTEGER);
            }
            
            pstmt.setInt(3, rendezVous.getLieu().getId());
            pstmt.setTimestamp(4, Timestamp.valueOf(rendezVous.getDate()));
            pstmt.setInt(5, rendezVous.getId());
            
            pstmt.executeUpdate();
            
        } catch (SQLException e) {
            System.err.println("Erreur lors de la mise à jour du rendez-vous: " + e.getMessage());
        }
    }
    
    public void delete(RendezVous rendezVous) {
        String sql = "DELETE FROM rendezvous WHERE id = ?";
        
        try (Connection conn = com.example.rendezvousmanager.database.DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, rendezVous.getId());
            pstmt.executeUpdate();
            
        } catch (SQLException e) {
            System.err.println("Erreur lors de la suppression du rendez-vous: " + e.getMessage());
        }
    }
}
