package com.example.rendezvousmanager.csv;

import com.example.rendezvousmanager.models.Client;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ClientCSV {
    private static final String CSV_FILE = "data/clients.csv";
    
    public ClientCSV() {
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
                writer.write("id,nom,prenom\n");
            } catch (IOException e) {
                System.err.println("Erreur lors de la création du fichier CSV: " + e.getMessage());
            }
        }
    }
    
    public List<Client> getAll() {
        List<Client> clients = new ArrayList<>();
        Path path = Paths.get(CSV_FILE);
        
        if (!Files.exists(path)) {
            return clients;
        }
        
        try (BufferedReader reader = Files.newBufferedReader(path)) {
            String line = reader.readLine(); // Skip header
            while ((line = reader.readLine()) != null) {
                String[] values = line.split(",");
                if (values.length >= 3) {
                    try {
                        int id = Integer.parseInt(values[0].trim());
                        String nom = values[1].trim();
                        String prenom = values[2].trim();
                        clients.add(new Client(id, nom, prenom));
                    } catch (NumberFormatException e) {
                        System.err.println("Erreur de format pour l'ID dans la ligne: " + line);
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Erreur lors de la lecture du fichier CSV: " + e.getMessage());
        }
        
        return clients;
    }
    
    public void add(Client client) {
        List<Client> clients = getAll();
        int newId = clients.isEmpty() ? 1 : clients.stream()
                .mapToInt(Client::getId)
                .max()
                .orElse(0) + 1;
        
        client.setId(newId);
        
        try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(CSV_FILE), 
                java.nio.file.StandardOpenOption.APPEND)) {
            writer.write(String.format("%d,%s,%s\n", 
                    client.getId(), 
                    escapeCsv(client.getNom()), 
                    escapeCsv(client.getPrenom())));
        } catch (IOException e) {
            System.err.println("Erreur lors de l'écriture dans le fichier CSV: " + e.getMessage());
        }
    }
    
    public void update(Client client) {
        List<Client> clients = getAll();
        boolean found = false;
        
        for (int i = 0; i < clients.size(); i++) {
            if (clients.get(i).getId() == client.getId()) {
                clients.set(i, client);
                found = true;
                break;
            }
        }
        
        if (found) {
            writeAllClients(clients);
        }
    }
    
    public void delete(Client client) {
        List<Client> clients = getAll();
        clients.removeIf(c -> c.getId() == client.getId());
        writeAllClients(clients);
    }
    
    private void writeAllClients(List<Client> clients) {
        try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(CSV_FILE))) {
            writer.write("id,nom,prenom\n");
            for (Client client : clients) {
                writer.write(String.format("%d,%s,%s\n", 
                        client.getId(), 
                        escapeCsv(client.getNom()), 
                        escapeCsv(client.getPrenom())));
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
