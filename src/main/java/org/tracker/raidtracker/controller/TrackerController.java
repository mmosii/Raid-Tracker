package org.tracker.raidtracker.controller;

import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.tracker.raidtracker.service.RssFeedService;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.awt.Desktop;
import java.net.URI;

public class TrackerController {
    @FXML
    public Label rbListLabel;
    @FXML
    public VBox rightMenu;
    @FXML
    private GridPane bossGrid;
    @FXML
    private TextField newBossField;
    @FXML
    private Button addNewBossButton;
    @FXML
    private Button removeBossButton;
    @FXML
    private TextArea consoleArea;
    @FXML
    private Label statusLabel;
    @FXML
    private Hyperlink telegramLink;
    @FXML
    private Label copyMessageLabel;
    @FXML
    private ListView<String> rbList;
    @FXML
    private VBox infoPane;
    @FXML
    private Button infoButton;
    @FXML
    private Button backButton;
    @FXML
    private Button prime;
    @FXML
    private Button phoenix;
    @FXML
    private Button asterios;
    @FXML
    private Button hunter;
    @FXML
    private Button medea;

    private final RssFeedService rssFeedService = new RssFeedService();

    private int bossCounter = 0;

    public void initialize() {
        createBossEntries();
        rbList.getItems().addAll(rssFeedService.getRbList());
        infoButton.setOnAction(e -> showInfoPane());
        backButton.setOnAction(e -> showMainPane());
        infoButton.setOnMouseEntered(e -> infoButton.setStyle("-fx-background-color: #FFD700; -fx-font-size: 16px; " +
                "-fx-padding: 10; -fx-border-color: black; -fx-border-width: 2; -fx-background-radius: 10; -fx-border-radius: 10;"));
        infoButton.setOnMouseExited(e -> infoButton.setStyle("-fx-background-color: #FFC300; -fx-font-size: 16px; " +
                "-fx-padding: 10; -fx-border-color: black; -fx-border-width: 2; -fx-background-radius: 10; -fx-border-radius: 10;"));
        addNewBossButton.setOnAction(e -> {
            String newBossName = newBossField.getText();
            if (!newBossName.isEmpty()) {
                addNewBoss(newBossName);
                newBossField.clear();
            }
        });

        prime.setOnAction(e -> {
            rssFeedService.setServerID("3");
            prime.setStyle("-fx-border-color: green; -fx-border-width: 2; -fx-font-size: 16px; -fx-background-color: lightgray; " +
                    "-fx-background-radius: 5; -fx-border-radius: 5;");
            asterios.setStyle("-fx-font-size: 16px; -fx-background-color: lightgray; -fx-background-radius: 5; -fx-border-radius: 5;");
            medea.setStyle("-fx-font-size: 16px; -fx-background-color: lightgray; -fx-background-radius: 5; -fx-border-radius: 5;");
            hunter.setStyle("-fx-font-size: 16px; -fx-background-color: lightgray; -fx-background-radius: 5; -fx-border-radius: 5;");
            phoenix.setStyle("-fx-font-size: 16px; -fx-background-color: lightgray; -fx-background-radius: 5; -fx-border-radius: 5;");
            rssFeedService.getRbTime().clear();
            rssFeedService.monitorRssFeed(message -> {
                Platform.runLater(() -> consoleArea.appendText(message + "\n"));
            });
        });

        asterios.setOnAction(e -> {
            rssFeedService.setServerID("0");
            asterios.setStyle("-fx-border-color: green; -fx-border-width: 2; -fx-font-size: 16px; -fx-background-color: lightgray; " +
                    "-fx-background-radius: 5; -fx-border-radius: 5;");
            prime.setStyle("-fx-font-size: 16px; -fx-background-color: lightgray; -fx-background-radius: 5; -fx-border-radius: 5;");
            medea.setStyle("-fx-font-size: 16px; -fx-background-color: lightgray; -fx-background-radius: 5; -fx-border-radius: 5;");
            hunter.setStyle("-fx-font-size: 16px; -fx-background-color: lightgray; -fx-background-radius: 5; -fx-border-radius: 5;");
            phoenix.setStyle("-fx-font-size: 16px; -fx-background-color: lightgray; -fx-background-radius: 5; -fx-border-radius: 5;");
            rssFeedService.getRbTime().clear();
            rssFeedService.monitorRssFeed(message -> {
                Platform.runLater(() -> consoleArea.appendText(message + "\n"));
            });
        });

        medea.setOnAction(e -> {
            rssFeedService.setServerID("6");
            medea.setStyle("-fx-border-color: green; -fx-border-width: 2; -fx-font-size: 16px; -fx-background-color: lightgray; " +
                    "-fx-background-radius: 5; -fx-border-radius: 5;");
            prime.setStyle("-fx-font-size: 16px; -fx-background-color: lightgray; -fx-background-radius: 5; -fx-border-radius: 5;");
            asterios.setStyle("-fx-font-size: 16px; -fx-background-color: lightgray; -fx-background-radius: 5; -fx-border-radius: 5;");
            hunter.setStyle("-fx-font-size: 16px; -fx-background-color: lightgray; -fx-background-radius: 5; -fx-border-radius: 5;");
            phoenix.setStyle("-fx-font-size: 16px; -fx-background-color: lightgray; -fx-background-radius: 5; -fx-border-radius: 5;");
            rssFeedService.getRbTime().clear();
            rssFeedService.monitorRssFeed(message -> {
                Platform.runLater(() -> consoleArea.appendText(message + "\n"));
            });
        });

        hunter.setOnAction(e -> {
            rssFeedService.setServerID("2");
            hunter.setStyle("-fx-border-color: green; -fx-border-width: 2; -fx-font-size: 16px; -fx-background-color: lightgray; " +
                    "-fx-background-radius: 5; -fx-border-radius: 5;");
            prime.setStyle("-fx-font-size: 16px; -fx-background-color: lightgray; -fx-background-radius: 5; -fx-border-radius: 5;");
            asterios.setStyle("-fx-font-size: 16px; -fx-background-color: lightgray; -fx-background-radius: 5; -fx-border-radius: 5;");
            medea.setStyle("-fx-font-size: 16px; -fx-background-color: lightgray; -fx-background-radius: 5; -fx-border-radius: 5;");
            phoenix.setStyle("-fx-font-size: 16px; -fx-background-color: lightgray; -fx-background-radius: 5; -fx-border-radius: 5;");
            rssFeedService.getRbTime().clear();
            rssFeedService.monitorRssFeed(message -> {
                Platform.runLater(() -> consoleArea.appendText(message + "\n"));
            });
        });

        phoenix.setOnAction(e -> {
            rssFeedService.setServerID("8");
            phoenix.setStyle("-fx-border-color: green; -fx-border-width: 2; -fx-font-size: 16px; -fx-background-color: lightgray; " +
                    "-fx-background-radius: 5; -fx-border-radius: 5;");
            prime.setStyle("-fx-font-size: 16px; -fx-background-color: lightgray; -fx-background-radius: 5; -fx-border-radius: 5;");
            asterios.setStyle("-fx-font-size: 16px; -fx-background-color: lightgray; -fx-background-radius: 5; -fx-border-radius: 5;");
            medea.setStyle("-fx-font-size: 16px; -fx-background-color: lightgray; -fx-background-radius: 5; -fx-border-radius: 5;");
            hunter.setStyle("-fx-font-size: 16px; -fx-background-color: lightgray; -fx-background-radius: 5; -fx-border-radius: 5;");
            rssFeedService.getRbTime().clear();
            rssFeedService.monitorRssFeed(message -> {
                Platform.runLater(() -> consoleArea.appendText(message + "\n"));
            });
        });

        removeBossButton.setOnAction(e -> {
            String newBossName = newBossField.getText();
            if (!newBossName.isEmpty()) {
                removeBoss(newBossName);
                newBossField.clear();
            }
        });

        telegramLink.setOnAction(event -> {
            try {
                Desktop.getDesktop().browse(new URI("https://t.me/dudewhereismycar_bot"));
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        new Thread(() -> {
            while (true) {
                Platform.runLater(() -> statusLabel.setText("Updated: " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"))));
                rssFeedService.monitorRssFeed(message -> {
                    Platform.runLater(() -> consoleArea.appendText(message + "\n"));
                });
                try {
                    Thread.sleep(10000);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }
        }).start();
    }

    private void createBossEntries() {
        String[] bosses = {"Cabrio", "Hallate", "Golkonda", "Kernon"};
        for (String bossName : bosses) {
            addBossToGrid(bossName);
        }
    }

    private void addNewBoss(String bossName) {
        rssFeedService.addBoss(bossName);
        rbList.getItems().add(bossName);
        rssFeedService.monitorRssFeed(message -> {
            Platform.runLater(() -> consoleArea.appendText(message + "\n"));
        });
    }

    private void removeBoss(String bossName) {
        rssFeedService.removeBoss(bossName);
        rbList.getItems().remove(bossName);
    }

    private void addBossToGrid(String bossName) {
        VBox bossBox = new VBox(5);
        bossBox.setPadding(new Insets(10));
        bossBox.setPrefWidth(300);

        StackPane namePane = new StackPane();
        Label nameLabel = new Label(bossName);
        nameLabel.setFont(Font.font("Verdana", 20));
        nameLabel.setTextFill(Color.BLACK);
        namePane.getChildren().add(nameLabel);
        StackPane.setAlignment(nameLabel, Pos.CENTER);

        StackPane statusPane = new StackPane();
        Label statusLabel = new Label(getBossStatus(bossName));
        statusLabel.setFont(Font.font("Verdana", 14));
        statusLabel.setTextFill(Color.BLUE);
        statusPane.getChildren().add(statusLabel);
        StackPane.setAlignment(statusLabel, Pos.CENTER);

        bossBox.getChildren().addAll(namePane, statusPane);

        applyShadowEffect(bossBox);
        updateButtonStyle(bossBox, rssFeedService.isBossInList(bossName));

        bossBox.setOnMouseClicked(event -> {
            boolean isInList = rssFeedService.isBossInList(bossName);
            if (isInList) {
                rssFeedService.removeBoss(bossName);
                rbList.getItems().remove(bossName);
            } else {
                rssFeedService.addBoss(bossName);
                rbList.getItems().add(bossName);
            }
            updateButtonStyle(bossBox, !isInList);
            rssFeedService.monitorRssFeed(message -> {
                Platform.runLater(() -> consoleArea.appendText(message + "\n"));
            });
            statusLabel.setText(getBossStatus(bossName));
        });

        BackgroundFill fill = new BackgroundFill(Color.LIGHTBLUE, new CornerRadii(30), null);

        Button copyButton = new Button("");
        switch (bossName) {
            case "Cabrio":
                copyButton.setText("CEM");
                break;
            case "Kernon":
                copyButton.setText("TOI 8");
                break;
            case "Golkonda":
                copyButton.setText("TOI 11");
                break;
            case "Hallate":
                copyButton.setText("TOI 3");
                break;
            default:
                copyButton.setText("⎘");
        }
        copyButton.setPrefWidth(50);
        copyButton.setPrefHeight(50);
        copyButton.setFont(Font.font("Tahoma", 10));
        copyButton.setBackground(new Background(fill));
        copyButton.setStyle(" -fx-border-radius: 30; -fx-border-color: black; -fx-border-width: 2;");
        copyButton.setOnMouseEntered(e -> {
            BackgroundFill hoverFill = new BackgroundFill(Color.GRAY, new CornerRadii(30), null);
            copyButton.setBackground(new Background(hoverFill));
        });

        copyButton.setOnMouseExited(e -> {
            BackgroundFill normalFill = new BackgroundFill(Color.LIGHTBLUE, new CornerRadii(30), null);
            copyButton.setBackground(new Background(normalFill));
        });
        copyButton.setOnAction(event -> {
            Clipboard clipboard = Clipboard.getSystemClipboard();
            ClipboardContent content = new ClipboardContent();

            switch (bossName) {
                case "Kernon":
                    content.putString("/target Chest of Kernon");
                    break;
                case "Golkonda":
                    content.putString("/target Chest of Golkonda");
                    break;
                case "Cabrio":
                    content.putString("/target Coffer of the Dead");
                    break;
                case "Hallate":
                    content.putString("/target Hallate's chest");
                    break;
                default:
                    content.putString(bossName);
            }
            clipboard.setContent(content);

            copyMessageLabel.setText("Target copied");
            copyMessageLabel.setVisible(true);

            PauseTransition pause = new PauseTransition(Duration.seconds(2));
            pause.setOnFinished(e -> copyMessageLabel.setVisible(false));
            pause.play();
        });
        StackPane.setMargin(copyButton, new Insets(5));

        StackPane wrapperPane = new StackPane(bossBox, copyButton);
        StackPane.setAlignment(copyButton, (bossCounter % 2 == 0) ? Pos.CENTER_RIGHT : Pos.CENTER_LEFT);
        bossGrid.add(wrapperPane, bossCounter % 2, bossCounter / 2);
        bossCounter++;

        new Thread(() -> {
            while (true) {
                Platform.runLater(() -> statusLabel.setText(getBossStatus(bossName)));
                try {
                    Thread.sleep(10000);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }
        }).start();
    }


    private String getBossStatus(String bossName) {
        LocalDateTime respawnTime = rssFeedService.getRespawnTime(bossName);

        if (respawnTime == null) {
            return "Update required";
        }

        LocalDateTime now = LocalDateTime.now();
        if (now.isAfter(respawnTime.plusDays(1).minusHours(6))) {
            return "Ongoing Respawn";
        } else {
            String timeLeft = formatTime(java.time.Duration.between(now, respawnTime.plusDays(1).minusHours(6)).toMinutes());
            return "Till respawn: " + timeLeft + "h";
        }
    }

    private void updateButtonStyle(VBox bossBox, boolean isInList) {
        if (isInList) {
            bossBox.setStyle("-fx-background-color: green; -fx-background-radius: 15; -fx-border-radius: 15;");
            bossBox.setOnMouseEntered(e -> bossBox.setStyle("-fx-background-color: #66FF66; -fx-background-radius: 15; -fx-border-radius: 15;"));
            bossBox.setOnMouseExited(e -> bossBox.setStyle("-fx-background-color: green; -fx-background-radius: 15; -fx-border-radius: 15;"));
        } else {
            bossBox.setStyle("-fx-background-color: red; -fx-background-radius: 15; -fx-border-radius: 15;");
            bossBox.setOnMouseEntered(e -> bossBox.setStyle("-fx-background-color: #FF6666; -fx-background-radius: 15; -fx-border-radius: 15;"));
            bossBox.setOnMouseExited(e -> bossBox.setStyle("-fx-background-color: red; -fx-background-radius: 15; -fx-border-radius: 15;"));
        }
    }

    private void applyShadowEffect(VBox bossBox) {
        DropShadow shadow = new DropShadow();
        shadow.setOffsetX(3);
        shadow.setOffsetY(3);
        shadow.setColor(Color.GRAY);
        bossBox.setEffect(shadow);
    }

    private static String formatTime(long minutes) {
        long hours = minutes / 60;
        long remainingMinutes = minutes % 60;
        return String.format("%02d:%02d", hours, remainingMinutes);
    }

    @FXML
    private void handleInfoButton() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/InfoWindow.fxml"));
            Parent root = loader.load();

            Stage infoStage = new Stage();
            infoStage.setTitle("Raid Tracker Guide");
            infoStage.initModality(Modality.APPLICATION_MODAL);
            infoStage.setScene(new Scene(root, 600, 400));

            infoStage.showAndWait();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void showInfoPane() {
        rightMenu.setVisible(false);
        bossGrid.setVisible(false);
        infoPane.setVisible(true);
    }

    private void showMainPane() {
        rightMenu.setVisible(true);
        bossGrid.setVisible(true);
        infoPane.setVisible(false);
    }
}
