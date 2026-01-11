package com.example.rendezvousmanager.models;

import javafx.beans.property.*;

public class Utilisateur {

    private final IntegerProperty id = new SimpleIntegerProperty();
    private final StringProperty prenom = new SimpleStringProperty();
    private final StringProperty nom = new SimpleStringProperty();
    private final StringProperty email = new SimpleStringProperty();

    public Utilisateur() {}

    public Utilisateur(int id, String prenom, String nom, String email) {
        this.id.set(id);
        this.prenom.set(prenom);
        this.nom.set(nom);
        this.email.set(email);
    }

    public Utilisateur(String prenom, String nom) {
        this.prenom.set(prenom);
        this.nom.set(nom);
        this.email.set(""); // email vide par défaut
    }

    public Utilisateur(String prenom, String nom, String email) {
        this.prenom.set(prenom);
        this.nom.set(nom);
        this.email.set(email);
    }

    public int getId() { return id.get(); }
    public void setId(int id) { this.id.set(id); }
    public IntegerProperty idProperty() { return id; }

    public String getPrenom() { return prenom.get(); }
    public void setPrenom(String prenom) { this.prenom.set(prenom); }
    public StringProperty prenomProperty() { return prenom; }

    public String getNom() { return nom.get(); }
    public void setNom(String nom) { this.nom.set(nom); }
    public StringProperty nomProperty() { return nom; }

    public String getEmail() { return email.get(); }
    public void setEmail(String email) { this.email.set(email); }
    public StringProperty emailProperty() { return email; }
}
