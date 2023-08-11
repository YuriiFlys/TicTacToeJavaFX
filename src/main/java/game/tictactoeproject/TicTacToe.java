package game.tictactoeproject;

import game.tictactoeproject.GameWithAI.MenuWithAI;
import game.tictactoeproject.GameWithFriend.GameWithFriend;
import javafx.application.*;
import javafx.geometry.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.*;
import javafx.stage.*;

public class TicTacToe extends Application {
    private boolean isDarkTheme = false;
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Хрестики - нулики");
        Label titleLabel = new Label("Хрестики - нулики");
        titleLabel.setFont(Font.font("Arial", FontWeight.BOLD, 32));
        Button playWithAIButton = new Button("Гра з ботом");
        playWithAIButton.setMinWidth(200);
        playWithAIButton.setMinHeight(50);
        playWithAIButton.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        Button playWithFriend = new Button("Гра з другом");
        playWithFriend.setMinWidth(200);
        playWithFriend.setMinHeight(50);
        playWithFriend.setFont(Font.font("Arial", FontWeight.BOLD, 22));
        Button themeButton = new Button("Змінити тему");
        themeButton.setMinWidth(200);
        themeButton.setMinHeight(50);
        themeButton.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        VBox menuBox = new VBox();
        menuBox.setAlignment(Pos.CENTER);
        menuBox.setSpacing(10);
        menuBox.getChildren().addAll(titleLabel, playWithAIButton,playWithFriend, themeButton);

        Scene menuScene = new Scene(menuBox, 700, 600);

        playWithFriend.setOnAction(event -> {
            GameWithFriend game = new GameWithFriend(menuScene, isDarkTheme);
            game.start(primaryStage);
        });

        playWithAIButton.setOnAction(event -> {
            MenuWithAI aiMenu = new MenuWithAI(menuScene, isDarkTheme);
            aiMenu.start(primaryStage);
        });

        themeButton.setOnAction(event -> {
            if (isDarkTheme) {
                menuScene.getRoot().setStyle("-fx-background-color: white");
                titleLabel.setStyle("-fx-text-fill: black");
                playWithAIButton.setStyle("-fx-text-fill: black");
                playWithFriend.setStyle("-fx-text-fill: black");
                themeButton.setStyle("-fx-text-fill: black;");
                isDarkTheme = false;
            } else {
                menuScene.getRoot().setStyle("-fx-background-color: black");
                titleLabel.setStyle("-fx-text-fill: white");
                playWithAIButton.setStyle("-fx-text-fill: white;-fx-background-color: black;-fx-border-color: white");
                playWithFriend.setStyle("-fx-text-fill: white;-fx-background-color: black;-fx-border-color: white");
                themeButton.setStyle("-fx-text-fill: white;-fx-background-color: black;-fx-border-color: white");
                isDarkTheme = true;
            }
        });
        primaryStage.setScene(menuScene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}




