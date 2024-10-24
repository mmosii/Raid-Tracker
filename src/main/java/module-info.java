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

    opens org.tracker.raidtracker to javafx.fxml;
    exports org.tracker.raidtracker;
}