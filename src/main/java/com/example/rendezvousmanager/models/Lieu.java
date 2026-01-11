package com.example.rendezvousmanager.models;

import javafx.beans.property.*;

public class Lieu {

    private final IntegerProperty id = new SimpleIntegerProperty();
    private final StringProperty nom = new SimpleStringProperty();
    private final StringProperty adresse = new SimpleStringProperty();

    public Lieu() {}

    public Lieu(int id, String nom, String adresse) {
        this.id.set(id);
        this.nom.set(nom);
        this.adresse.set(adresse);
    }

    public Lieu(String nom) {
        this.nom.set(nom);
        this.adresse.set(""); // adresse vide par défaut
    }

    public Lieu(String nom, String adresse) {
        this.nom.set(nom);
        this.adresse.set(adresse);
    }

    public int getId() { return id.get(); }
    public void setId(int id) { this.id.set(id); }
    public IntegerProperty idProperty() { return id; }

    public String getNom() { return nom.get(); }
    public void setNom(String nom) { this.nom.set(nom); }
    public StringProperty nomProperty() { return nom; }

    public String getAdresse() { return adresse.get(); }
    public void setAdresse(String adresse) { this.adresse.set(adresse); }
    public StringProperty adresseProperty() { return adresse; }
}
