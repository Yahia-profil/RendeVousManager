package com.example.rendezvousmanager.models;

import javafx.beans.property.*;
import java.time.LocalDateTime;

public class RendezVous {
    private final IntegerProperty id = new SimpleIntegerProperty();
    private final ObjectProperty<Client> client = new SimpleObjectProperty<>();
    private final ObjectProperty<CategorieRendezVous> categorie = new SimpleObjectProperty<>();
    private final ObjectProperty<Lieu> lieu = new SimpleObjectProperty<>();
    private final ObjectProperty<LocalDateTime> date = new SimpleObjectProperty<>();

    public RendezVous() {}

    public RendezVous(int id, Client client, CategorieRendezVous categorie, Lieu lieu, LocalDateTime date) {
        this.id.set(id);
        this.client.set(client);
        this.categorie.set(categorie);
        this.lieu.set(lieu);
        this.date.set(date);
    }

    public int getId() { return id.get(); }
    public void setId(int id) { this.id.set(id); }
    public IntegerProperty idProperty() { return id; }

    public Client getClient() { return client.get(); }
    public void setClient(Client c) { client.set(c); }
    public ObjectProperty<Client> clientProperty() { return client; }

    public CategorieRendezVous getCategorie() { return categorie.get(); }
    public void setCategorie(CategorieRendezVous cat) { categorie.set(cat); }
    public ObjectProperty<CategorieRendezVous> categorieProperty() { return categorie; }

    public Lieu getLieu() { return lieu.get(); }
    public void setLieu(Lieu l) { lieu.set(l); }
    public ObjectProperty<Lieu> lieuProperty() { return lieu; }

    public LocalDateTime getDate() { return date.get(); }
    public void setDate(LocalDateTime d) { date.set(d); }
    public ObjectProperty<LocalDateTime> dateProperty() { return date; }
}
