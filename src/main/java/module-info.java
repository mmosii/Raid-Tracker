module org.tracker.raidtracker {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires telegrambots;
    requires telegrambots.meta;
    requires com.rometools.rome;
    requires java.desktop;

    opens org.tracker.raidtracker to javafx.fxml;
    exports org.tracker.raidtracker;
    exports org.tracker.raidtracker.controller;
    opens org.tracker.raidtracker.controller to javafx.fxml;
    exports org.tracker.raidtracker.service;
    opens org.tracker.raidtracker.service to javafx.fxml;
    exports org.tracker.raidtracker.handler;
    opens org.tracker.raidtracker.handler to javafx.fxml;
    exports org.tracker.raidtracker.telegram;
    opens org.tracker.raidtracker.telegram to javafx.fxml;
}