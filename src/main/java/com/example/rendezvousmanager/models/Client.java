package com.example.rendezvousmanager.models;

import javafx.beans.property.*;

public class Client {
    private final IntegerProperty id = new SimpleIntegerProperty();
    private final StringProperty nom = new SimpleStringProperty();
    private final StringProperty prenom = new SimpleStringProperty();

    public Client() {}

    public Client(String nom, String prenom) {
        this.nom.set(nom);
        this.prenom.set(prenom);
    }

    public Client(int id, String nom, String prenom) {
        this.id.set(id);
        this.nom.set(nom);
        this.prenom.set(prenom);
    }

    public int getId() { return id.get(); }
    public void setId(int id) { this.id.set(id); }
    public IntegerProperty idProperty() { return id; }

    public String getNom() { return nom.get(); }
    public void setNom(String nom) { this.nom.set(nom); }
    public StringProperty nomProperty() { return nom; }

    public String getPrenom() { return prenom.get(); }
    public void setPrenom(String prenom) { this.prenom.set(prenom); }
    public StringProperty prenomProperty() { return prenom; }
}
