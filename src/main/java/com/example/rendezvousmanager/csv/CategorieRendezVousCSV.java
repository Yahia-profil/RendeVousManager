package com.example.rendezvousmanager.csv;

import com.example.rendezvousmanager.models.CategorieRendezVous;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class CategorieRendezVousCSV {
    private static final String CSV_FILE = "data/categories.csv";
    
    public CategorieRendezVousCSV() {
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
                writer.write("id,nom,description\n");
            } catch (IOException e) {
                System.err.println("Erreur lors de la création du fichier CSV: " + e.getMessage());
            }
        }
    }
    
    public List<CategorieRendezVous> getAll() {
        List<CategorieRendezVous> categories = new ArrayList<>();
        Path path = Paths.get(CSV_FILE);
        
        if (!Files.exists(path)) {
            return categories;
        }
        
        try (BufferedReader reader = Files.newBufferedReader(path)) {
            String line = reader.readLine(); // Skip header
            while ((line = reader.readLine()) != null) {
                String[] values = line.split(",");
                if (values.length >= 3) {
                    try {
                        int id = Integer.parseInt(values[0].trim());
                        String nom = values[1].trim();
                        String description = values[2].trim();
                        categories.add(new CategorieRendezVous(id, nom, description));
                    } catch (NumberFormatException e) {
                        System.err.println("Erreur de format pour l'ID dans la ligne: " + line);
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Erreur lors de la lecture du fichier CSV: " + e.getMessage());
        }
        
        return categories;
    }
    
    public void add(CategorieRendezVous categorie) {
        List<CategorieRendezVous> categories = getAll();
        int newId = categories.isEmpty() ? 1 : categories.stream()
                .mapToInt(CategorieRendezVous::getId)
                .max()
                .orElse(0) + 1;
        
        categorie.setId(newId);
        
        try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(CSV_FILE), 
                java.nio.file.StandardOpenOption.APPEND)) {
            writer.write(String.format("%d,%s,%s\n", 
                    categorie.getId(), 
                    escapeCsv(categorie.getNom()), 
                    escapeCsv(categorie.getDescription())));
        } catch (IOException e) {
            System.err.println("Erreur lors de l'écriture dans le fichier CSV: " + e.getMessage());
        }
    }
    
    public void update(CategorieRendezVous categorie) {
        List<CategorieRendezVous> categories = getAll();
        boolean found = false;
        
        for (int i = 0; i < categories.size(); i++) {
            if (categories.get(i).getId() == categorie.getId()) {
                categories.set(i, categorie);
                found = true;
                break;
            }
        }
        
        if (found) {
            writeAllCategories(categories);
        }
    }
    
    public void delete(CategorieRendezVous categorie) {
        List<CategorieRendezVous> categories = getAll();
        categories.removeIf(c -> c.getId() == categorie.getId());
        writeAllCategories(categories);
    }
    
    private void writeAllCategories(List<CategorieRendezVous> categories) {
        try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(CSV_FILE))) {
            writer.write("id,nom,description\n");
            for (CategorieRendezVous categorie : categories) {
                writer.write(String.format("%d,%s,%s\n", 
                        categorie.getId(), 
                        escapeCsv(categorie.getNom()), 
                        escapeCsv(categorie.getDescription())));
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
