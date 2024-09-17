module lk.projects.library {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;
    requires java.sql;
    requires static lombok;
    requires java.desktop;

    opens lk.projects.library to javafx.fxml;
    exports lk.projects.library;
    exports lk.projects.library.controller;
    opens lk.projects.library.controller to javafx.fxml;
    opens lk.projects.library.entity to javafx.base;
}