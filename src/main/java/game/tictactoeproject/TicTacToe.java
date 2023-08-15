package game.tictactoeproject;

import game.tictactoeproject.GameWithAI.MenuWithAI;
import game.tictactoeproject.GameWithFriend.GameWithFriend;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.*;
import javafx.geometry.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.text.*;
import javafx.stage.*;
import javafx.scene.media.*;
import javafx.util.Duration;
import java.io.File;

public class TicTacToe extends Application {
    private boolean isDarkTheme = false;
    public static boolean isEnglish = false;
    String pathToSoundTrack = "D:\\Java(Homework)\\TicTacToeProject\\src\\main\\java\\game\\tictactoeproject\\SoundTrack\\Soundtrack.mp3";
    String pathToSoundClick = "D:\\Java(Homework)\\TicTacToeProject\\src\\main\\java\\game\\tictactoeproject\\SoundTrack\\click.mp3";
    Media soundTrack = new Media(new File(pathToSoundTrack).toURI().toString());
    Media soundClick = new Media(new File(pathToSoundClick).toURI().toString());
    Image background_white = new Image("file:D:\\Java(Homework)\\TicTacToeProject\\src\\main\\java\\game\\tictactoeproject\\Background\\background_white.jpg");
    Image background_black = new Image("file:D:\\Java(Homework)\\TicTacToeProject\\src\\main\\java\\game\\tictactoeproject\\Background\\background_black.jpg");

    MediaPlayer mediaPlayer = new MediaPlayer(soundTrack);
    MediaPlayer mediaPlayerClick = new MediaPlayer(soundClick);
    DropShadow shadow = new DropShadow();

    public void start(Stage primaryStage) {

        primaryStage.setTitle("Хрестики - нулики");
        Label titleLabel = new Label("Хрестики - нулики");
        titleLabel.setFont(Font.font("Arial", FontWeight.BOLD, 32));
        titleLabel.setStyle("-fx-text-fill: black");
        titleLabel.setEffect(shadow);
        Button playWithAIButton = new Button("Гра з ботом");
        mediaPlayer.setVolume(0.04);
        mediaPlayer.play();
        playWithAIButton.setMinWidth(200);
        playWithAIButton.setMinHeight(50);
        playWithAIButton.setFont(Font.font("Arial", FontWeight.BOLD, 22));
        Button playWithFriend = new Button("Гра з другом");
        playWithFriend.setMinWidth(200);
        playWithFriend.setMinHeight(50);
        playWithFriend.setFont(Font.font("Arial", FontWeight.BOLD, 22));
        Button themeButton = new Button("Змінити тему");
        themeButton.setMinWidth(200);
        themeButton.setMinHeight(50);
        themeButton.setFont(Font.font("Arial", FontWeight.BOLD, 22));
        Button languageButton = new Button("Змінити мову");
        Label projectLabel = new Label("Project by Yurii Flys");
        projectLabel.setFont(Font.font("Arial", FontWeight.BOLD, 15));
        projectLabel.setStyle("-fx-text-fill: black");
        projectLabel.setEffect(shadow);
        VBox menuBox = new VBox();
        menuBox.setAlignment(Pos.CENTER);
        menuBox.setSpacing(10);
        menuBox.getChildren().addAll(titleLabel, playWithAIButton,playWithFriend, themeButton,projectLabel);



        Scene menuScene = new Scene(menuBox, 700, 600);
        BackgroundImage background = new BackgroundImage(
                background_white,
                BackgroundRepeat.REPEAT,
                BackgroundRepeat.REPEAT,
                BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT
        );
        BackgroundImage background1 = new BackgroundImage(
                background_black,
                BackgroundRepeat.REPEAT,
                BackgroundRepeat.REPEAT,
                BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT
        );
        menuBox.setBackground(new Background(background));
        primaryStage.setScene(menuScene);
        playWithFriend.setOnAction(event -> {
            mediaPlayerClick.setVolume(0.2);
            mediaPlayerClick.stop();
            mediaPlayerClick.play();
            Timeline timeline = new Timeline(new KeyFrame(
                    Duration.millis(1),
                    ae -> mediaPlayerClick.play()));
            timeline.play();
            GameWithFriend game = new GameWithFriend(menuScene, isDarkTheme);
            game.start(primaryStage);
        });
        playWithAIButton.setOnAction(event -> {
            mediaPlayerClick.setVolume(0.2);
            mediaPlayerClick.stop();
            mediaPlayerClick.play();
            Timeline timeline = new Timeline(new KeyFrame(
                    Duration.millis(1),
                    ae -> mediaPlayerClick.play()));
            timeline.play();
            MenuWithAI aiMenu = new MenuWithAI(menuScene, isDarkTheme);
            aiMenu.start(primaryStage);
        });

        themeButton.setOnAction(event -> {
            mediaPlayerClick.setVolume(0.2);
            mediaPlayerClick.stop();
            mediaPlayerClick.play();
            Timeline timeline = new Timeline(new KeyFrame(
                    Duration.millis(1),
                    ae -> mediaPlayerClick.play()));
            timeline.play();
            if (isDarkTheme) {
                titleLabel.setEffect(shadow);
                projectLabel.setEffect(shadow);
                menuBox.setBackground(new Background(background));
                titleLabel.setStyle("-fx-text-fill: black");
                playWithAIButton.setStyle("-fx-text-fill: black");
                playWithFriend.setStyle("-fx-text-fill: black");
                themeButton.setStyle("-fx-text-fill: black;");
                languageButton.setStyle("-fx-text-fill: black;");
                isDarkTheme = false;
            } else {
                titleLabel.setEffect(shadow);
                projectLabel.setEffect(shadow);
                menuBox.setBackground(new Background(background1));
                titleLabel.setStyle("-fx-text-fill: white");
                projectLabel.setStyle("-fx-text-fill: white");
                playWithAIButton.setStyle("-fx-text-fill: white;-fx-background-color: black;-fx-border-color: white");
                playWithFriend.setStyle("-fx-text-fill: white;-fx-background-color: black;-fx-border-color: white");
                themeButton.setStyle("-fx-text-fill: white;-fx-background-color: black;-fx-border-color: white");
                isDarkTheme = true;
            }
        });
        playWithAIButton.setEffect(shadow);
        playWithFriend.setEffect(shadow);
        themeButton.setEffect(shadow);
        primaryStage.setScene(menuScene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}




