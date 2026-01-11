package com.example.rendezvousmanager.models;

import javafx.beans.property.*;

public class CategorieRendezVous {

    private final IntegerProperty id = new SimpleIntegerProperty();
    private final StringProperty nom = new SimpleStringProperty();
    private final StringProperty description = new SimpleStringProperty();

    public CategorieRendezVous() {}

    public CategorieRendezVous(int id, String nom, String description) {
        this.id.set(id);
        this.nom.set(nom);
        this.description.set(description);
    }

    public CategorieRendezVous(String nom) {
        this.nom.set(nom);
        this.description.set(""); // description vide par défaut
    }

    public CategorieRendezVous(String nom, String description) {
        this.nom.set(nom);
        this.description.set(description);
    }

    public int getId() { return id.get(); }
    public void setId(int id) { this.id.set(id); }
    public IntegerProperty idProperty() { return id; }

    public String getNom() { return nom.get(); }
    public void setNom(String nom) { this.nom.set(nom); }
    public StringProperty nomProperty() { return nom; }

    public String getDescription() { return description.get(); }
    public void setDescription(String description) { this.description.set(description); }
    public StringProperty descriptionProperty() { return description; }
}
