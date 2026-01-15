package com.example.rendezvousmanager.jdbc;

import com.example.rendezvousmanager.models.Utilisateur;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UtilisateurJDBC {
    
    public List<Utilisateur> getAll() {
        List<Utilisateur> utilisateurs = new ArrayList<>();
        String sql = "SELECT id, nom, prenom, email FROM utilisateurs ORDER BY nom, prenom";
        
        try (Connection conn = com.example.rendezvousmanager.database.DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                utilisateurs.add(new Utilisateur(
                    rs.getInt("id"),
                    rs.getString("nom"),
                    rs.getString("prenom"),
                    rs.getString("email")
                ));
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la lecture des utilisateurs: " + e.getMessage());
        }
        
        return utilisateurs;
    }
    
    public void add(Utilisateur utilisateur) {
        String sql = "INSERT INTO utilisateurs (nom, prenom, email) VALUES (?, ?, ?)";
        
        try (Connection conn = com.example.rendezvousmanager.database.DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            pstmt.setString(1, utilisateur.getNom());
            pstmt.setString(2, utilisateur.getPrenom());
            pstmt.setString(3, utilisateur.getEmail());
            pstmt.executeUpdate();
            
            // Récupérer l'ID généré
            try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    utilisateur.setId(generatedKeys.getInt(1));
                }
            }
            
        } catch (SQLException e) {
            System.err.println("Erreur lors de l'ajout de l'utilisateur: " + e.getMessage());
        }
    }
    
    public void update(Utilisateur utilisateur) {
        String sql = "UPDATE utilisateurs SET nom = ?, prenom = ?, email = ? WHERE id = ?";
        
        try (Connection conn = com.example.rendezvousmanager.database.DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, utilisateur.getNom());
            pstmt.setString(2, utilisateur.getPrenom());
            pstmt.setString(3, utilisateur.getEmail());
            pstmt.setInt(4, utilisateur.getId());
            pstmt.executeUpdate();
            
        } catch (SQLException e) {
            System.err.println("Erreur lors de la mise à jour de l'utilisateur: " + e.getMessage());
        }
    }
    
    public void delete(Utilisateur utilisateur) {
        String sql = "DELETE FROM utilisateurs WHERE id = ?";
        
        try (Connection conn = com.example.rendezvousmanager.database.DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, utilisateur.getId());
            pstmt.executeUpdate();
            
        } catch (SQLException e) {
            System.err.println("Erreur lors de la suppression de l'utilisateur: " + e.getMessage());
        }
    }
    
    public Utilisateur findById(int id) {
        String sql = "SELECT id, nom, prenom, email FROM utilisateurs WHERE id = ?";
        
        try (Connection conn = com.example.rendezvousmanager.database.DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, id);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return new Utilisateur(
                        rs.getInt("id"),
                        rs.getString("nom"),
                        rs.getString("prenom"),
                        rs.getString("email")
                    );
                }
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la recherche de l'utilisateur: " + e.getMessage());
        }
        
        return null;
    }
}
