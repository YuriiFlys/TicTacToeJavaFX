package game.tictactoeproject.GameWithAI;

import game.tictactoeproject.Logic.GameState;
import game.tictactoeproject.Logic.GameLogic;
import game.tictactoeproject.Logic.Player;
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
import javafx.animation.*;
import javafx.util.*;

import java.io.File;

public class HardGame extends Application {
    String pathToSoundTrack = "D:\\Java(Homework)\\TicTacToeProject\\src\\main\\java\\game\\tictactoeproject\\SoundTrack\\Soundtrack.mp3";
    String pathToSoundClick = "D:\\Java(Homework)\\TicTacToeProject\\src\\main\\java\\game\\tictactoeproject\\SoundTrack\\click.mp3";

    Image background_white = new Image("file:D:\\Java(Homework)\\TicTacToeProject\\src\\main\\java\\game\\tictactoeproject\\Background\\background_white.jpg");
    Image background_black = new Image("file:D:\\Java(Homework)\\TicTacToeProject\\src\\main\\java\\game\\tictactoeproject\\Background\\background_black.jpg");
    // Create new ImageView objects with the background images
    ImageView backgroundImageView = new ImageView(background_white);
    ImageView backgroundImageView1 = new ImageView(background_black);

    // Create a new GaussianBlur effect with the desired radius
    GaussianBlur blurEffect = new GaussianBlur(25);
    Media soundTrack = new Media(new File(pathToSoundTrack).toURI().toString());
    Media soundClick = new Media(new File(pathToSoundClick).toURI().toString());

    MediaPlayer mediaPlayer = new MediaPlayer(soundTrack);
    MediaPlayer mediaPlayerClick = new MediaPlayer(soundClick);
    DropShadow shadow = new DropShadow();
    private Scene menuScene;
    Player player = new Player("Гравець", 'X');
    Player computer = new Player("Бот", 'O');
    private boolean isDarkTheme;
    boolean isBotTurn = false;
    public HardGame(Scene menuScene, boolean isDarkTheme) {
        this.menuScene = menuScene;
        this.isDarkTheme = isDarkTheme;
    }
    Player currentPlayer = player;
    Timeline timeline1 = new Timeline();
    private char[][] board = new char[3][3];

    private boolean gameOver = false;
    private int playerScore = 0;
    private int computerScore = 0;
    PauseTransition pause = new PauseTransition(Duration.seconds(3));
    private Label statusLabel = new Label("Твій хід.");
    private Label playerScoreLabel = new Label("Гравець: 0");
    private Label computerScoreLabel = new Label("Бот: 0");
    private void resetGame(Button[][] buttons) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                board[i][j] = '\u0000';
                buttons[i][j].setText(" ");
            }
        }
        currentPlayer = player;
        gameOver = false;
        isBotTurn = false;
        statusLabel.setText("Твій хід.");
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

    private int minimax(char[][] board, boolean isMaximizing, Player currentPlayer) {

        if (getGameState(board) == GameState.X_WON || getGameState(board) == GameState.O_WON) {
            return isMaximizing ? -1 : 1;
        }
        if (getGameState(board) == GameState.DRAW) {
            return 0;
        }

        int bestScore = isMaximizing ? Integer.MIN_VALUE : Integer.MAX_VALUE;
        Player nextPlayer = (currentPlayer == player) ? computer : player;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == '\u0000') {
                    board[i][j] = nextPlayer.getSign();
                    int score = minimax(board, !isMaximizing, nextPlayer);
                    board[i][j] = '\u0000';
                    bestScore = isMaximizing ? Math.max(score, bestScore) : Math.min(score, bestScore);
                }
            }
        }
        return bestScore;
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
                    if (!gameOver && board[finalI][finalJ] == '\u0000' && !isBotTurn) {
                        mediaPlayerClick.setVolume(0.2);
                        mediaPlayerClick.stop();
                        mediaPlayerClick.play();
                        Timeline timeline = new Timeline(new KeyFrame(
                                Duration.millis(1),
                                ae -> mediaPlayerClick.play()));
                        timeline.play();
                        button.setText(String.valueOf(currentPlayer.getSign()));
                        board[finalI][finalJ] = currentPlayer.getSign();
                        if(isDarkTheme){
                            button.setEffect(new Glow(2));
                            button.setStyle("-fx-text-fill: red;-fx-background-color: black;-fx-border-color: white");
                        }
                        if (getGameState(board)==GameState.X_WON || getGameState(board)==GameState.O_WON) {
                            statusLabel.setText(currentPlayer.getName() + " переміг!");
                            if (currentPlayer.getSign() == 'X') {
                                playerScore++;
                                playerScoreLabel.setText("Гравець: " + playerScore);
                            } else {
                                computerScore++;
                                computerScoreLabel.setText("Бот: " + computerScore);
                            }
                            gameOver = true;
                        } else if (getGameState(board)==GameState.DRAW) {
                            statusLabel.setText("Нічия!");
                            gameOver = true;
                        } else {
                            currentPlayer = (currentPlayer == player)? computer : player;
                            if (currentPlayer.getSign() == 'O') {
                                isBotTurn = true;
                                timeline1.getKeyFrames().addAll(
                                        new KeyFrame(Duration.seconds(0), event1 -> statusLabel.setText("Бот думає.")),
                                        new KeyFrame(Duration.seconds(0.5), event1 -> statusLabel.setText("Бот думає..")),
                                        new KeyFrame(Duration.seconds(1), event1 -> statusLabel.setText("Бот думає...")),
                                        new KeyFrame(Duration.seconds(1.5), event1 -> statusLabel.setText("Бот думає."))
                                );
                                timeline1.setCycleCount(Animation.INDEFINITE);
                                timeline1.play();
                                pause.setOnFinished(event1 -> {
                                    // Тут розміщуємо код, який буде виконаний після закінчення затримки
                                    // Наприклад, хід бота
                                    int bestScore = Integer.MIN_VALUE;
                                    int bestMoveI = -1;
                                    int bestMoveJ = -1;
                                    for (int k = 0; k < 3; k++) {
                                        for (int l = 0; l < 3; l++) {
                                            if (board[k][l] == '\u0000') {
                                                board[k][l] = currentPlayer.getSign();
                                                int score = minimax(board, false, currentPlayer);
                                                board[k][l] = '\u0000';
                                                if (score > bestScore) {
                                                    bestScore = score;
                                                    bestMoveI = k;
                                                    bestMoveJ = l;
                                                }
                                            }
                                        }
                                    }
                                    mediaPlayerClick.setVolume(0.2);
                                    mediaPlayerClick.stop();
                                    mediaPlayerClick.play();
                                    Timeline timeline2 = new Timeline(new KeyFrame(
                                            Duration.millis(1),
                                            ae -> mediaPlayerClick.play()));
                                    timeline2.play();
                                    board[bestMoveI][bestMoveJ] = currentPlayer.getSign();
                                    buttons[bestMoveI][bestMoveJ].setText(String.valueOf(currentPlayer.getSign()));
                                    timeline1.stop();
                                    if(isDarkTheme){
                                        buttons[bestMoveI][bestMoveJ].setEffect(new Glow(2));
                                        buttons[bestMoveI][bestMoveJ].setStyle("-fx-text-fill: blue;-fx-background-color: black;-fx-border-color: white");
                                    }
                                    if (getGameState(board)==GameState.X_WON || getGameState(board)==GameState.O_WON) {
                                        statusLabel.setText(currentPlayer.getName() + " переміг!");
                                        if (currentPlayer.getSign() == 'X') {
                                            playerScore++;
                                            playerScoreLabel.setText("Гравець: " + playerScore);
                                        } else {
                                            computerScore++;
                                            computerScoreLabel.setText("Бот: " + computerScore);
                                        }
                                        gameOver = true;
                                    } else if (getGameState(board)==GameState.DRAW) {
                                        statusLabel.setText("Нічия!");
                                        gameOver = true;
                                    } else {
                                        currentPlayer = (currentPlayer == player)? computer : player;
                                        statusLabel.setText("Бот зробив хід, тепер твоя черга.");
                                        isBotTurn = false;
                                    }
                                });

                                // Запускаємо анімацію затримки
                                pause.play();
                            }
                        }
                    }
                });
                grid.add(button, i, j);
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
            playerScore = 0;
            computerScore = 0;
            playerScoreLabel.setText("Гравець: " + playerScore);
            computerScoreLabel.setText("Бот: " + computerScore);
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
            primaryStage.setScene(menuScene);
            primaryStage.show();
        });

        Label titleLabel = new Label("Хрестики-нулики");
        titleLabel.setFont(Font.font("Arial", FontWeight.BOLD, 32));

        statusLabel.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        playerScoreLabel.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        computerScoreLabel.setFont(Font.font("Arial", FontWeight.BOLD, 24));

        VBox infoBox = new VBox();
        infoBox.setAlignment(Pos.CENTER);
        infoBox.setSpacing(10);
        infoBox.setMinWidth(300);
        infoBox.getChildren().addAll(backButton,titleLabel, statusLabel, playerScoreLabel, computerScoreLabel, resetButton, resetScoreButton);

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
            statusLabel.setStyle("-fx-text-fill: white");
            playerScoreLabel.setStyle("-fx-text-fill: white");
            computerScoreLabel.setStyle("-fx-text-fill: white");
            resetButton.setStyle("-fx-text-fill: white;-fx-background-color: black;-fx-border-color: white");
            resetScoreButton.setStyle("-fx-text-fill: white;-fx-background-color: black;-fx-border-color: white");
            backButton.setStyle("-fx-text-fill: white;-fx-background-color: black;-fx-border-color: white");
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    buttons[i][j].setStyle("-fx-background-color: black; -fx-border-color: white;");

                }
            }
        }

        resetButton.setEffect(shadow);
        resetScoreButton.setEffect(shadow);
        backButton.setEffect(shadow);

        primaryStage.setScene(scene);
        primaryStage.setMinWidth(650);
        primaryStage.setMinHeight(300);
        primaryStage.show();
    }


}


