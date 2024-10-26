package org.tracker.raidtracker;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.util.Objects;

public class TrackerApplication extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/tracker.fxml")));
        primaryStage.setTitle("Raid Tracker");
        primaryStage.setScene(new Scene(root, 860, 750));
        primaryStage.getIcons().add(new Image("icon.png"));
        primaryStage.setOnCloseRequest(event -> {
            System.out.println("Application is closing...");
            Platform.exit();
            System.exit(0);
        });
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
