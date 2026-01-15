package com.example.rendezvousmanager.jdbc;

import com.example.rendezvousmanager.models.Client;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClientJDBC {
    
    public List<Client> getAll() {
        List<Client> clients = new ArrayList<>();
        String sql = "SELECT id, nom, prenom FROM clients ORDER BY nom, prenom";
        
        try (Connection conn = com.example.rendezvousmanager.database.DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                clients.add(new Client(
                    rs.getInt("id"),
                    rs.getString("nom"),
                    rs.getString("prenom")
                ));
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la lecture des clients: " + e.getMessage());
        }
        
        return clients;
    }
    
    public void add(Client client) {
        String sql = "INSERT INTO clients (nom, prenom) VALUES (?, ?)";
        
        try (Connection conn = com.example.rendezvousmanager.database.DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            pstmt.setString(1, client.getNom());
            pstmt.setString(2, client.getPrenom());
            pstmt.executeUpdate();
            
            // Récupérer l'ID généré
            try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    client.setId(generatedKeys.getInt(1));
                }
            }
            
        } catch (SQLException e) {
            System.err.println("Erreur lors de l'ajout du client: " + e.getMessage());
        }
    }
    
    public void update(Client client) {
        String sql = "UPDATE clients SET nom = ?, prenom = ? WHERE id = ?";
        
        try (Connection conn = com.example.rendezvousmanager.database.DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, client.getNom());
            pstmt.setString(2, client.getPrenom());
            pstmt.setInt(3, client.getId());
            pstmt.executeUpdate();
            
        } catch (SQLException e) {
            System.err.println("Erreur lors de la mise à jour du client: " + e.getMessage());
        }
    }
    
    public void delete(Client client) {
        String sql = "DELETE FROM clients WHERE id = ?";
        
        try (Connection conn = com.example.rendezvousmanager.database.DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, client.getId());
            pstmt.executeUpdate();
            
        } catch (SQLException e) {
            System.err.println("Erreur lors de la suppression du client: " + e.getMessage());
        }
    }
    
    public Client findById(int id) {
        String sql = "SELECT id, nom, prenom FROM clients WHERE id = ?";
        
        try (Connection conn = com.example.rendezvousmanager.database.DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, id);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return new Client(
                        rs.getInt("id"),
                        rs.getString("nom"),
                        rs.getString("prenom")
                    );
                }
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la recherche du client: " + e.getMessage());
        }
        
        return null;
    }
}
