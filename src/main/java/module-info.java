module com.example.rendezvousmanager {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.example.rendezvousmanager to javafx.fxml;
    opens com.example.rendezvousmanager.controllers to javafx.fxml;
    opens com.example.rendezvousmanager.models to javafx.base;

    exports com.example.rendezvousmanager;
    exports com.example.rendezvousmanager.controllers;
    exports com.example.rendezvousmanager.models;
    exports com.example.rendezvousmanager.services;
    exports com.example.rendezvousmanager.dao;
}
