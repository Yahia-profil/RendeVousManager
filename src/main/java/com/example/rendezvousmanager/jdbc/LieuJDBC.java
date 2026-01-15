package com.example.rendezvousmanager.jdbc;

import com.example.rendezvousmanager.models.Lieu;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LieuJDBC {
    
    public List<Lieu> getAll() {
        List<Lieu> lieux = new ArrayList<>();
        String sql = "SELECT id, nom, adresse FROM lieux ORDER BY nom";
        
        try (Connection conn = com.example.rendezvousmanager.database.DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                lieux.add(new Lieu(
                    rs.getInt("id"),
                    rs.getString("nom"),
                    rs.getString("adresse")
                ));
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la lecture des lieux: " + e.getMessage());
        }
        
        return lieux;
    }
    
    public void add(Lieu lieu) {
        String sql = "INSERT INTO lieux (nom, adresse) VALUES (?, ?)";
        
        try (Connection conn = com.example.rendezvousmanager.database.DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            pstmt.setString(1, lieu.getNom());
            pstmt.setString(2, lieu.getAdresse());
            pstmt.executeUpdate();
            
            // Récupérer l'ID généré
            try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    lieu.setId(generatedKeys.getInt(1));
                }
            }
            
        } catch (SQLException e) {
            System.err.println("Erreur lors de l'ajout du lieu: " + e.getMessage());
        }
    }
    
    public void update(Lieu lieu) {
        String sql = "UPDATE lieux SET nom = ?, adresse = ? WHERE id = ?";
        
        try (Connection conn = com.example.rendezvousmanager.database.DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, lieu.getNom());
            pstmt.setString(2, lieu.getAdresse());
            pstmt.setInt(3, lieu.getId());
            pstmt.executeUpdate();
            
        } catch (SQLException e) {
            System.err.println("Erreur lors de la mise à jour du lieu: " + e.getMessage());
        }
    }
    
    public void delete(Lieu lieu) {
        String sql = "DELETE FROM lieux WHERE id = ?";
        
        try (Connection conn = com.example.rendezvousmanager.database.DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, lieu.getId());
            pstmt.executeUpdate();
            
        } catch (SQLException e) {
            System.err.println("Erreur lors de la suppression du lieu: " + e.getMessage());
        }
    }
    
    public Lieu findById(int id) {
        String sql = "SELECT id, nom, adresse FROM lieux WHERE id = ?";
        
        try (Connection conn = com.example.rendezvousmanager.database.DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, id);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return new Lieu(
                        rs.getInt("id"),
                        rs.getString("nom"),
                        rs.getString("adresse")
                    );
                }
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la recherche du lieu: " + e.getMessage());
        }
        
        return null;
    }
}
