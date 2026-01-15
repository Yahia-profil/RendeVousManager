package com.example.rendezvousmanager.csv;

import com.example.rendezvousmanager.models.RendezVous;
import com.example.rendezvousmanager.models.Client;
import com.example.rendezvousmanager.models.CategorieRendezVous;
import com.example.rendezvousmanager.models.Lieu;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class RendezVousCSV {
    private static final String CSV_FILE = "data/rendezvous.csv";
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    
    private final ClientCSV clientCSV;
    private final CategorieRendezVousCSV categorieCSV;
    private final LieuCSV lieuCSV;
    
    public RendezVousCSV() {
        this.clientCSV = new ClientCSV();
        this.categorieCSV = new CategorieRendezVousCSV();
        this.lieuCSV = new LieuCSV();
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
                writer.write("id,clientId,categorieId,lieuId,date\n");
            } catch (IOException e) {
                System.err.println("Erreur lors de la création du fichier CSV: " + e.getMessage());
            }
        }
    }
    
    public List<RendezVous> getAll() {
        List<RendezVous> rendezVousList = new ArrayList<>();
        Path path = Paths.get(CSV_FILE);
        
        if (!Files.exists(path)) {
            return rendezVousList;
        }
        
        try (BufferedReader reader = Files.newBufferedReader(path)) {
            String line = reader.readLine(); // Skip header
            while ((line = reader.readLine()) != null) {
                String[] values = line.split(",");
                if (values.length >= 5) {
                    try {
                        int id = Integer.parseInt(values[0].trim());
                        int clientId = Integer.parseInt(values[1].trim());
                        int categorieId = Integer.parseInt(values[2].trim());
                        int lieuId = Integer.parseInt(values[3].trim());
                        LocalDateTime date = LocalDateTime.parse(values[4].trim(), DATE_FORMATTER);
                        
                        // Récupérer les objets associés
                        Client client = findClientById(clientId);
                        CategorieRendezVous categorie = (categorieId > 0) ? findCategorieById(categorieId) : null;
                        Lieu lieu = findLieuById(lieuId);
                        
                        if (client != null && lieu != null) {
                            rendezVousList.add(new RendezVous(id, client, categorie, lieu, date));
                        }
                    } catch (NumberFormatException e) {
                        System.err.println("Erreur de format pour l'ID dans la ligne: " + line);
                    } catch (Exception e) {
                        System.err.println("Erreur de format de date dans la ligne: " + line);
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Erreur lors de la lecture du fichier CSV: " + e.getMessage());
        }
        
        return rendezVousList;
    }
    
    public void add(RendezVous rendezVous) {
        List<RendezVous> rendezVousList = getAll();
        int newId = rendezVousList.isEmpty() ? 1 : rendezVousList.stream()
                .mapToInt(RendezVous::getId)
                .max()
                .orElse(0) + 1;
        
        rendezVous.setId(newId);
        
        try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(CSV_FILE), 
                java.nio.file.StandardOpenOption.APPEND)) {
            int categorieId = (rendezVous.getCategorie() != null) ? rendezVous.getCategorie().getId() : 0;
            writer.write(String.format("%d,%d,%d,%d,%s\n", 
                    rendezVous.getId(), 
                    rendezVous.getClient().getId(),
                    categorieId,
                    rendezVous.getLieu().getId(),
                    rendezVous.getDate().format(DATE_FORMATTER)));
        } catch (IOException e) {
            System.err.println("Erreur lors de l'écriture dans le fichier CSV: " + e.getMessage());
        }
    }
    
    public void update(RendezVous rendezVous) {
        List<RendezVous> rendezVousList = getAll();
        boolean found = false;
        
        for (int i = 0; i < rendezVousList.size(); i++) {
            if (rendezVousList.get(i).getId() == rendezVous.getId()) {
                rendezVousList.set(i, rendezVous);
                found = true;
                break;
            }
        }
        
        if (found) {
            writeAllRendezVous(rendezVousList);
        }
    }
    
    public void delete(RendezVous rendezVous) {
        List<RendezVous> rendezVousList = getAll();
        rendezVousList.removeIf(r -> r.getId() == rendezVous.getId());
        writeAllRendezVous(rendezVousList);
    }
    
    private void writeAllRendezVous(List<RendezVous> rendezVousList) {
        try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(CSV_FILE))) {
            writer.write("id,clientId,categorieId,lieuId,date\n");
            for (RendezVous rendezVous : rendezVousList) {
                int categorieId = (rendezVous.getCategorie() != null) ? rendezVous.getCategorie().getId() : 0;
                writer.write(String.format("%d,%d,%d,%d,%s\n", 
                        rendezVous.getId(), 
                        rendezVous.getClient().getId(),
                        categorieId,
                        rendezVous.getLieu().getId(),
                        rendezVous.getDate().format(DATE_FORMATTER)));
            }
        } catch (IOException e) {
            System.err.println("Erreur lors de l'écriture dans le fichier CSV: " + e.getMessage());
        }
    }
    
    private Client findClientById(int id) {
        return clientCSV.getAll().stream()
                .filter(c -> c.getId() == id)
                .findFirst()
                .orElse(null);
    }
    
    private CategorieRendezVous findCategorieById(int id) {
        return categorieCSV.getAll().stream()
                .filter(c -> c.getId() == id)
                .findFirst()
                .orElse(null);
    }
    
    private Lieu findLieuById(int id) {
        return lieuCSV.getAll().stream()
                .filter(l -> l.getId() == id)
                .findFirst()
                .orElse(null);
    }
}
