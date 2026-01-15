package com.example.rendezvousmanager.jdbc;

import com.example.rendezvousmanager.models.CategorieRendezVous;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CategorieRendezVousJDBC {
    
    public List<CategorieRendezVous> getAll() {
        List<CategorieRendezVous> categories = new ArrayList<>();
        String sql = "SELECT id, nom, description FROM categories ORDER BY nom";
        
        try (Connection conn = com.example.rendezvousmanager.database.DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                categories.add(new CategorieRendezVous(
                    rs.getInt("id"),
                    rs.getString("nom"),
                    rs.getString("description")
                ));
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la lecture des catégories: " + e.getMessage());
        }
        
        return categories;
    }
    
    public void add(CategorieRendezVous categorie) {
        String sql = "INSERT INTO categories (nom, description) VALUES (?, ?)";
        
        try (Connection conn = com.example.rendezvousmanager.database.DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            pstmt.setString(1, categorie.getNom());
            pstmt.setString(2, categorie.getDescription());
            pstmt.executeUpdate();
            
            // Récupérer l'ID généré
            try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    categorie.setId(generatedKeys.getInt(1));
                }
            }
            
        } catch (SQLException e) {
            System.err.println("Erreur lors de l'ajout de la catégorie: " + e.getMessage());
        }
    }
    
    public void update(CategorieRendezVous categorie) {
        String sql = "UPDATE categories SET nom = ?, description = ? WHERE id = ?";
        
        try (Connection conn = com.example.rendezvousmanager.database.DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, categorie.getNom());
            pstmt.setString(2, categorie.getDescription());
            pstmt.setInt(3, categorie.getId());
            pstmt.executeUpdate();
            
        } catch (SQLException e) {
            System.err.println("Erreur lors de la mise à jour de la catégorie: " + e.getMessage());
        }
    }
    
    public void delete(CategorieRendezVous categorie) {
        String sql = "DELETE FROM categories WHERE id = ?";
        
        try (Connection conn = com.example.rendezvousmanager.database.DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, categorie.getId());
            pstmt.executeUpdate();
            
        } catch (SQLException e) {
            System.err.println("Erreur lors de la suppression de la catégorie: " + e.getMessage());
        }
    }
    
    public CategorieRendezVous findById(int id) {
        String sql = "SELECT id, nom, description FROM categories WHERE id = ?";
        
        try (Connection conn = com.example.rendezvousmanager.database.DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, id);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return new CategorieRendezVous(
                        rs.getInt("id"),
                        rs.getString("nom"),
                        rs.getString("description")
                    );
                }
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la recherche de la catégorie: " + e.getMessage());
        }
        
        return null;
    }
}
