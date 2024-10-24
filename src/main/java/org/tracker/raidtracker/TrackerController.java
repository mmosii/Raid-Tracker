package org.tracker.raidtracker;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

    public class TrackerController {

        @FXML
        private ListView<String> rbListView;
        @FXML
        private TextArea consoleArea;
        @FXML
        private TextField newBossField;
        @FXML
        private Button addBossButton;
        @FXML
        private Button removeBossButton;

        private RssFeedService rssFeedService = new RssFeedService();

        public void initialize() {
            // Populate the initial boss list
            rbListView.getItems().addAll(rssFeedService.getRbList());

            // Add boss button event
            addBossButton.setOnAction(e -> {
                String bossName = newBossField.getText();
                if (!bossName.isEmpty()) {
                    rssFeedService.addBoss(bossName);
                    rbListView.getItems().add(bossName);
                    newBossField.clear();
                    rbListView.getItems().addAll(rssFeedService.getRbList());
                }
            });

            // Remove boss button event
            removeBossButton.setOnAction(e -> {
                String selectedBoss = rbListView.getSelectionModel().getSelectedItem();
                if (selectedBoss != null) {
                    rssFeedService.removeBoss(selectedBoss);
                    rbListView.getItems().remove(selectedBoss);
                    newBossField.clear();
                    rbListView.getItems().addAll(rssFeedService.getRbList());
                }
            });

            // Start monitoring the RSS feed
            new Thread(() -> {
                while (true) {
                    rssFeedService.monitorRssFeed(new ConsoleOutputHandler() {
                        @Override
                        public void print(String message) {
                            Platform.runLater(() -> consoleArea.appendText(message + "\n"));
                        }
                    });

                    try {
                        Thread.sleep(60000);  // Poll RSS feed every 60 seconds
                    } catch (InterruptedException ex) {
                        ex.printStackTrace();
                    }
                }
            }).start();
        }
    }