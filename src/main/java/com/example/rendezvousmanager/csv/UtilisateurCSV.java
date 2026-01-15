package com.example.rendezvousmanager.csv;

import com.example.rendezvousmanager.models.Utilisateur;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class UtilisateurCSV {
    private static final String CSV_FILE = "data/utilisateurs.csv";
    
    public UtilisateurCSV() {
        createDataDirectory();
        createCsvFileIfNotExists();
    }
    
    private void createDataDirectory() {
        try {
            Files.createDirectories(Paths.get("data"));
        } catch (IOException e) {
            System.err.println("Erreur lors de la création du répertoire data: " + e.getMessage());
        }
    }
    
    private void createCsvFileIfNotExists() {
        Path path = Paths.get(CSV_FILE);
        if (!Files.exists(path)) {
            try (BufferedWriter writer = Files.newBufferedWriter(path)) {
                writer.write("id,prenom,nom,email\n");
            } catch (IOException e) {
                System.err.println("Erreur lors de la création du fichier CSV: " + e.getMessage());
            }
        }
    }
    
    public List<Utilisateur> getAll() {
        List<Utilisateur> utilisateurs = new ArrayList<>();
        Path path = Paths.get(CSV_FILE);
        
        if (!Files.exists(path)) {
            return utilisateurs;
        }
        
        try (BufferedReader reader = Files.newBufferedReader(path)) {
            String line = reader.readLine(); // Skip header
            while ((line = reader.readLine()) != null) {
                String[] values = line.split(",");
                if (values.length >= 4) {
                    try {
                        int id = Integer.parseInt(values[0].trim());
                        String prenom = values[1].trim();
                        String nom = values[2].trim();
                        String email = values[3].trim();
                        utilisateurs.add(new Utilisateur(id, prenom, nom, email));
                    } catch (NumberFormatException e) {
                        System.err.println("Erreur de format pour l'ID dans la ligne: " + line);
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Erreur lors de la lecture du fichier CSV: " + e.getMessage());
        }
        
        return utilisateurs;
    }
    
    public void add(Utilisateur utilisateur) {
        List<Utilisateur> utilisateurs = getAll();
        int newId = utilisateurs.isEmpty() ? 1 : utilisateurs.stream()
                .mapToInt(Utilisateur::getId)
                .max()
                .orElse(0) + 1;
        
        utilisateur.setId(newId);
        
        try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(CSV_FILE), 
                java.nio.file.StandardOpenOption.APPEND)) {
            writer.write(String.format("%d,%s,%s,%s\n", 
                    utilisateur.getId(), 
                    escapeCsv(utilisateur.getPrenom()), 
                    escapeCsv(utilisateur.getNom()), 
                    escapeCsv(utilisateur.getEmail())));
        } catch (IOException e) {
            System.err.println("Erreur lors de l'écriture dans le fichier CSV: " + e.getMessage());
        }
    }
    
    public void update(Utilisateur utilisateur) {
        List<Utilisateur> utilisateurs = getAll();
        boolean found = false;
        
        for (int i = 0; i < utilisateurs.size(); i++) {
            if (utilisateurs.get(i).getId() == utilisateur.getId()) {
                utilisateurs.set(i, utilisateur);
                found = true;
                break;
            }
        }
        
        if (found) {
            writeAllUtilisateurs(utilisateurs);
        }
    }
    
    public void delete(Utilisateur utilisateur) {
        List<Utilisateur> utilisateurs = getAll();
        utilisateurs.removeIf(u -> u.getId() == utilisateur.getId());
        writeAllUtilisateurs(utilisateurs);
    }
    
    private void writeAllUtilisateurs(List<Utilisateur> utilisateurs) {
        try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(CSV_FILE))) {
            writer.write("id,prenom,nom,email\n");
            for (Utilisateur utilisateur : utilisateurs) {
                writer.write(String.format("%d,%s,%s,%s\n", 
                        utilisateur.getId(), 
                        escapeCsv(utilisateur.getPrenom()), 
                        escapeCsv(utilisateur.getNom()), 
                        escapeCsv(utilisateur.getEmail())));
            }
        } catch (IOException e) {
            System.err.println("Erreur lors de l'écriture dans le fichier CSV: " + e.getMessage());
        }
    }
    
    private String escapeCsv(String value) {
        if (value == null) return "";
        if (value.contains(",") || value.contains("\"") || value.contains("\n")) {
            return "\"" + value.replace("\"", "\"\"") + "\"";
        }
        return value;
    }
}
