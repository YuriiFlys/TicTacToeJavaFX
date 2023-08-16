package game.tictactoeproject.GameWithAI;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;

import static game.tictactoeproject.Logic.GameLogic.createButton;

public class MenuWithAI extends Application {
    String pathToSoundClick = "D:\\Java(Homework)\\TicTacToeProject\\src\\main\\java\\game\\tictactoeproject\\SoundTrack\\click.mp3";
    Media soundClick = new Media(new File(pathToSoundClick).toURI().toString());
    DropShadow shadow = new DropShadow();
    Image background_white = new Image("file:D:\\Java(Homework)\\TicTacToeProject\\src\\main\\java\\game\\tictactoeproject\\Background\\background_white.jpg");
    Image background_black = new Image("file:D:\\Java(Homework)\\TicTacToeProject\\src\\main\\java\\game\\tictactoeproject\\Background\\background_black.jpg");
    MediaPlayer mediaPlayerClick = new MediaPlayer(soundClick);
    private final Scene menuScene;
    private final boolean isDarkTheme;
    public MenuWithAI(Scene menuScene, boolean isDarkTheme) {
        this.menuScene = menuScene;
        this.isDarkTheme = isDarkTheme;
    }

    @Override
    public void start(Stage primaryStage) {
        Label titleLabel = new Label("Вибери складність:");
        titleLabel.setFont(Font.font("Arial", FontWeight.BOLD, 32));
        titleLabel.setStyle("-fx-text-fill: black");
        Button easyButton = createButton("Легко", event -> {
            mediaPlayerClick.setVolume(0.2);
            mediaPlayerClick.stop();
            mediaPlayerClick.play();
            Timeline timeline = new Timeline(new KeyFrame(
                    Duration.millis(1),
                    ae -> mediaPlayerClick.play()));
            timeline.play();
            EasyGame game = new EasyGame(menuScene, isDarkTheme);
            game.start(primaryStage);
        });
        Button hardButton = createButton("Складно", event -> {
            mediaPlayerClick.setVolume(0.2);
            mediaPlayerClick.stop();
            mediaPlayerClick.play();
            Timeline timeline = new Timeline(new KeyFrame(
                    Duration.millis(1),
                    ae -> mediaPlayerClick.play()));
            timeline.play();
            HardGame game = new HardGame(menuScene, isDarkTheme);
            game.start(primaryStage);
        });
        Button backButton = createButton("Назад", event -> {
            mediaPlayerClick.setVolume(0.2);
            mediaPlayerClick.stop();
            mediaPlayerClick.play();
            Timeline timeline = new Timeline(new KeyFrame(
                    Duration.millis(1),
                    ae -> mediaPlayerClick.play()));
            timeline.play();
            primaryStage.setScene(menuScene);
        });
        VBox menuBox = new VBox();
        menuBox.setAlignment(Pos.CENTER);
        menuBox.setSpacing(10);
        menuBox.getChildren().addAll(titleLabel, easyButton, hardButton, backButton);
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
        Scene aiMenuScene = new Scene(menuBox, 700, 600);

        if(isDarkTheme){
            menuBox.setBackground(new Background(background1));
            titleLabel.setStyle("-fx-text-fill: white");
            titleLabel.setEffect(shadow);

            easyButton.setStyle("-fx-text-fill: white;-fx-background-color: black;-fx-border-color: white");
            hardButton.setStyle("-fx-text-fill: white;-fx-background-color: black;-fx-border-color: white");
            backButton.setStyle("-fx-text-fill: white;-fx-background-color: black;-fx-border-color: white");
        }

        titleLabel.setEffect(shadow);
        backButton.setEffect(shadow);
        easyButton.setEffect(shadow);
        hardButton.setEffect(shadow);

        primaryStage.setScene(aiMenuScene);
        primaryStage.show();
    }
}
