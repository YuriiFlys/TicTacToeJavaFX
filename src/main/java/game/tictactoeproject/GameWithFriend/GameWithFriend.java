package game.tictactoeproject.GameWithFriend;
import game.tictactoeproject.Logic.GameState;
import game.tictactoeproject.Logic.GameLogic;
import game.tictactoeproject.Logic.Player;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.*;
import javafx.geometry.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.effect.Glow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.text.*;
import javafx.stage.*;
import javafx.util.Duration;

import java.io.File;


public class GameWithFriend extends Application {
    String pathToSoundClick = "D:\\Java(Homework)\\TicTacToeProject\\src\\main\\java\\game\\tictactoeproject\\SoundTrack\\click.mp3";
    Media soundClick = new Media(new File(pathToSoundClick).toURI().toString());
    MediaPlayer mediaPlayerClick = new MediaPlayer(soundClick);
    Image background_white = new Image("file:D:\\Java(Homework)\\TicTacToeProject\\src\\main\\java\\game\\tictactoeproject\\Background\\background_white.jpg");
    Image background_black = new Image("file:D:\\Java(Homework)\\TicTacToeProject\\src\\main\\java\\game\\tictactoeproject\\Background\\background_black.jpg");
    ImageView backgroundImageView = new ImageView(background_white);
    ImageView backgroundImageView1 = new ImageView(background_black);
    GaussianBlur blurEffect = new GaussianBlur(25);
    private final Scene aiMenuScene;
    private final boolean isDarkTheme;
    public GameWithFriend(Scene aiMenuScene, boolean isDarkTheme) {
        this.aiMenuScene = aiMenuScene;
        this.isDarkTheme = isDarkTheme;
    }
    private final char[][] board = new char[3][3];
    Player player1 = new Player("Гравець 1", 'X');
    Player player2 = new Player("Гравець 2", 'O');
    Player currentPlayer = player1;
    private boolean gameOver = false;
    DropShadow shadow = new DropShadow();

    private final Label turnLabel = new Label(currentPlayer.getName() + ", твій хід.");
    private final Label player1ScoreLabel = new Label("Гравець 1: 0");
    private final Label player2ScoreLabel = new Label("Гравець 2: 0");
    private int player1Score = 0;
    private int player2Score = 0;

    private void resetGame(Button[][] buttons) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                board[i][j] = '\u0000';
                buttons[i][j].setText(" ");
            }
        }
        currentPlayer = player1;
        gameOver = false;
        turnLabel.setText("Гравець 1, твій хід.");
    }
    public GameState getGameState(char[][] board) {
        if (GameLogic.checkWinner(board)) {
            if(currentPlayer.getSign() == 'X') {
                return GameState.X_WON;
            } else {
                return GameState.O_WON;
            }

        } else if (GameLogic.isDraw(board)) {
            return GameState.DRAW;
        } else {
            return GameState.IN_PROGRESS;
        }
    }
    @Override
    public void start(Stage primaryStage) {
        GridPane grid = new GridPane();
        Button[][] buttons = new Button[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                Button button = new Button(" ");
                button.setMinSize(200, 200);
                button.setFont(Font.font("Arial", FontWeight.BOLD, 72));
                button.setEffect(shadow);
                int finalI = i;
                int finalJ = j;
                button.setOnAction(event -> {
                    if (!gameOver && board[finalI][finalJ] == '\u0000') {
                        button.setText(String.valueOf(currentPlayer.getSign()));
                        board[finalI][finalJ] = currentPlayer.getSign();
                        if(isDarkTheme && currentPlayer.getSign() == 'X'){
                            button.setEffect(new Glow(2));
                            button.setStyle("-fx-text-fill: red;-fx-background-color: black;-fx-border-color: white");
                        }
                        else if(isDarkTheme && currentPlayer.getSign() == 'O'){
                            button.setEffect(new Glow(2));
                            button.setStyle("-fx-text-fill: blue;-fx-background-color: black;-fx-border-color: white");
                        }

                        if (getGameState(board) == GameState.X_WON || getGameState(board) == GameState.O_WON) {
                            turnLabel.setText(currentPlayer.getName() + " переміг!");
                            if (currentPlayer.getSign() == 'X') {
                                player1Score++;
                                player1ScoreLabel.setText("Гравець 1: " + player1Score);

                            } else {
                                player2Score++;
                                player2ScoreLabel.setText("Гравець 2: " + player2Score);
                            }
                            gameOver = true;
                        } else if (getGameState(board)==GameState.DRAW){
                            turnLabel.setText("Нічия!");
                            gameOver = true;
                        } else {
                            currentPlayer = (currentPlayer == player1) ? player2 : player1;
                            turnLabel.setText(currentPlayer.getName() + ", твій хід.");
                        }
                    }
                });
                grid.add(button, j, i);
                buttons[i][j] = button;
            }
        }
        Button resetButton = new Button("Рестарт");
        resetButton.setMinWidth(200);
        resetButton.setMinHeight(50);
        resetButton.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        resetButton.setOnAction(event -> {
            mediaPlayerClick.setVolume(0.2);
            mediaPlayerClick.stop();
            mediaPlayerClick.play();
            Timeline timeline = new Timeline(new KeyFrame(
                    Duration.millis(1),
                    ae -> mediaPlayerClick.play()));
            timeline.play();
            resetGame(buttons);
        });

        Button resetScoreButton = new Button("Обнулення");
        resetScoreButton.setMinWidth(200);
        resetScoreButton.setMinHeight(50);
        resetScoreButton.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        resetScoreButton.setOnAction(event -> {
            mediaPlayerClick.setVolume(0.2);
            mediaPlayerClick.stop();
            mediaPlayerClick.play();
            Timeline timeline = new Timeline(new KeyFrame(
                    Duration.millis(1),
                    ae -> mediaPlayerClick.play()));
            timeline.play();
            player1Score = 0;
            player2Score = 0;
            player1ScoreLabel.setText("Гравець 1: " + player1Score);
            player2ScoreLabel.setText("Гравець 2: " + player2Score);
        });

        Button backButton = new Button("Назад");
        backButton.setMinWidth(200);
        backButton.setMinHeight(50);
        backButton.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        backButton.setOnAction(event -> {
            mediaPlayerClick.setVolume(0.2);
            mediaPlayerClick.stop();
            mediaPlayerClick.play();
            Timeline timeline = new Timeline(new KeyFrame(
                    Duration.millis(1),
                    ae -> mediaPlayerClick.play()));
            timeline.play();
            primaryStage.setScene(aiMenuScene);
            primaryStage.show();
        });



        Label titleLabel = new Label("Хрестики-нулики");
        titleLabel.setFont(Font.font("Arial", FontWeight.BOLD, 32));

        turnLabel.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        player1ScoreLabel.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        player2ScoreLabel.setFont(Font.font("Arial", FontWeight.BOLD, 24));

        VBox infoBox = new VBox();
        infoBox.setAlignment(Pos.CENTER);
        infoBox.setSpacing(10);
        infoBox.setMinWidth(300);
        infoBox.getChildren().addAll(backButton,titleLabel, turnLabel, player1ScoreLabel, player2ScoreLabel, resetButton, resetScoreButton);

        HBox gameBox = new HBox();
        gameBox.setAlignment(Pos.CENTER);
        gameBox.setSpacing(30);
        gameBox.getChildren().addAll(grid);

        BorderPane border = new BorderPane();
        BorderPane.setMargin(gameBox, new Insets(10, 0, 0, 0));
        border.setTop(infoBox);
        border.setCenter(gameBox);

        backgroundImageView.setEffect(blurEffect);
        backgroundImageView1.setEffect(blurEffect);

        StackPane root = new StackPane(backgroundImageView, border);

        Scene scene = new Scene(root, 740, 960);
        if (isDarkTheme) {
            root.getChildren().setAll(backgroundImageView1, border);
            titleLabel.setStyle("-fx-text-fill: white");
            turnLabel.setStyle("-fx-text-fill: white");
            player1ScoreLabel.setStyle("-fx-text-fill: white");
            player2ScoreLabel.setStyle("-fx-text-fill: white");
            resetButton.setStyle("-fx-text-fill: white;-fx-background-color: black;-fx-border-color: white");
            resetScoreButton.setStyle("-fx-text-fill: white;-fx-background-color: black;-fx-border-color: white");
            backButton.setStyle("-fx-text-fill: white;-fx-background-color: black;-fx-border-color: white");
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    buttons[i][j].setStyle("-fx-background-color: black; -fx-border-color: white;");

                }
            }
        }

        backButton.setEffect(shadow);
        resetButton.setEffect(shadow);
        resetScoreButton.setEffect(shadow);

        primaryStage.setScene(scene);
        primaryStage.setMinWidth(650);
        primaryStage.setMinHeight(300);
        primaryStage.show();
    }

}