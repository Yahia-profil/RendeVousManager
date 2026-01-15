package com.example.rendezvousmanager.csv;

import com.example.rendezvousmanager.models.Lieu;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class LieuCSV {
    private static final String CSV_FILE = "data/lieux.csv";
    
    public LieuCSV() {
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
                writer.write("id,nom,adresse\n");
            } catch (IOException e) {
                System.err.println("Erreur lors de la création du fichier CSV: " + e.getMessage());
            }
        }
    }
    
    public List<Lieu> getAll() {
        List<Lieu> lieux = new ArrayList<>();
        Path path = Paths.get(CSV_FILE);
        
        if (!Files.exists(path)) {
            return lieux;
        }
        
        try (BufferedReader reader = Files.newBufferedReader(path)) {
            String line = reader.readLine(); // Skip header
            while ((line = reader.readLine()) != null) {
                String[] values = line.split(",");
                if (values.length >= 3) {
                    try {
                        int id = Integer.parseInt(values[0].trim());
                        String nom = values[1].trim();
                        String adresse = values[2].trim();
                        lieux.add(new Lieu(id, nom, adresse));
                    } catch (NumberFormatException e) {
                        System.err.println("Erreur de format pour l'ID dans la ligne: " + line);
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Erreur lors de la lecture du fichier CSV: " + e.getMessage());
        }
        
        return lieux;
    }
    
    public void add(Lieu lieu) {
        List<Lieu> lieux = getAll();
        int newId = lieux.isEmpty() ? 1 : lieux.stream()
                .mapToInt(Lieu::getId)
                .max()
                .orElse(0) + 1;
        
        lieu.setId(newId);
        
        try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(CSV_FILE), 
                java.nio.file.StandardOpenOption.APPEND)) {
            writer.write(String.format("%d,%s,%s\n", 
                    lieu.getId(), 
                    escapeCsv(lieu.getNom()), 
                    escapeCsv(lieu.getAdresse())));
        } catch (IOException e) {
            System.err.println("Erreur lors de l'écriture dans le fichier CSV: " + e.getMessage());
        }
    }
    
    public void update(Lieu lieu) {
        List<Lieu> lieux = getAll();
        boolean found = false;
        
        for (int i = 0; i < lieux.size(); i++) {
            if (lieux.get(i).getId() == lieu.getId()) {
                lieux.set(i, lieu);
                found = true;
                break;
            }
        }
        
        if (found) {
            writeAllLieux(lieux);
        }
    }
    
    public void delete(Lieu lieu) {
        List<Lieu> lieux = getAll();
        lieux.removeIf(l -> l.getId() == lieu.getId());
        writeAllLieux(lieux);
    }
    
    private void writeAllLieux(List<Lieu> lieux) {
        try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(CSV_FILE))) {
            writer.write("id,nom,adresse\n");
            for (Lieu lieu : lieux) {
                writer.write(String.format("%d,%s,%s\n", 
                        lieu.getId(), 
                        escapeCsv(lieu.getNom()), 
                        escapeCsv(lieu.getAdresse())));
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
