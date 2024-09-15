module lk.projects.library {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;
    requires java.sql;

    opens lk.projects.library to javafx.fxml;
    exports lk.projects.library;
    exports lk.projects.library.controller;
    opens lk.projects.library.controller to javafx.fxml;
}