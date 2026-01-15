package com.example.rendezvousmanager.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseConnection {
    private static final String URL = "jdbc:mysql://localhost:3307/rendezvous_db";
    private static final String USER = "root";
    private static final String PASSWORD = "";
    
    static {
        try {
            // Charger le driver MySQL
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.err.println("Driver MySQL non trouvé: " + e.getMessage());
        }
    }
    
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
    
    public static void initializeDatabase() {
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3307/", USER, PASSWORD);
             Statement stmt = conn.createStatement()) {
            
            // Créer la base de données si elle n'existe pas
            stmt.executeUpdate("CREATE DATABASE IF NOT EXISTS rendezvous_db");
            System.out.println("Base de données rendezvous_db créée ou déjà existante");
            
            // Créer les tables
            createTables();
            
        } catch (SQLException e) {
            System.err.println("Erreur lors de l'initialisation de la base de données: " + e.getMessage());
        }
    }
    
    private static void createTables() {
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement()) {
            
            // Table clients
            stmt.execute("CREATE TABLE IF NOT EXISTS clients (" +
                "id INT AUTO_INCREMENT PRIMARY KEY," +
                "nom VARCHAR(100) NOT NULL," +
                "prenom VARCHAR(100) NOT NULL" +
                ")");
            
            // Table categories
            stmt.execute("CREATE TABLE IF NOT EXISTS categories (" +
                "id INT AUTO_INCREMENT PRIMARY KEY," +
                "nom VARCHAR(100) NOT NULL," +
                "description TEXT" +
                ")");
            
            // Table lieux
            stmt.execute("CREATE TABLE IF NOT EXISTS lieux (" +
                "id INT AUTO_INCREMENT PRIMARY KEY," +
                "nom VARCHAR(100) NOT NULL," +
                "adresse VARCHAR(255)" +
                ")");
            
            // Table utilisateurs
            stmt.execute("CREATE TABLE IF NOT EXISTS utilisateurs (" +
                "id INT AUTO_INCREMENT PRIMARY KEY," +
                "nom VARCHAR(100) NOT NULL," +
                "prenom VARCHAR(100) NOT NULL," +
                "email VARCHAR(255) NOT NULL" +
                ")");
            
            // Table rendezvous
            stmt.execute("CREATE TABLE IF NOT EXISTS rendezvous (" +
                "id INT AUTO_INCREMENT PRIMARY KEY," +
                "client_id INT NOT NULL," +
                "categorie_id INT," +
                "lieu_id INT NOT NULL," +
                "date DATETIME NOT NULL," +
                "FOREIGN KEY (client_id) REFERENCES clients(id)," +
                "FOREIGN KEY (categorie_id) REFERENCES categories(id)," +
                "FOREIGN KEY (lieu_id) REFERENCES lieux(id)" +
                ")");
            
            System.out.println("Tables créées avec succès");
            
        } catch (SQLException e) {
            System.err.println("Erreur lors de la création des tables: " + e.getMessage());
        }
    }
}
