module com.example.rendezvousmanager {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires mysql.connector.j;

    opens com.example.rendezvousmanager to javafx.fxml;
    opens com.example.rendezvousmanager.controllers to javafx.fxml;
    opens com.example.rendezvousmanager.models to javafx.base;

    exports com.example.rendezvousmanager;
    exports com.example.rendezvousmanager.controllers;
    exports com.example.rendezvousmanager.models;
    exports com.example.rendezvousmanager.services;
    exports com.example.rendezvousmanager.dao;
    exports com.example.rendezvousmanager.database;
    exports com.example.rendezvousmanager.jdbc;
}
