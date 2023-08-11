package game.tictactoeproject.GameWithAI;

import game.tictactoeproject.Logic.GameLogic;
import game.tictactoeproject.Logic.Player;
import game.tictactoeproject.Logic.GameState;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.geometry.Pos;
import javafx.scene.layout.HBox;
import javafx.scene.layout.BorderPane;
public class HardGame extends Application {

    private Scene menuScene;
    Player player = new Player("Гравець", 'X');
    Player computer = new Player("Бот", 'O');
    private boolean isDarkTheme;
    public HardGame(Scene menuScene, boolean isDarkTheme) {
        this.menuScene = menuScene;
        this.isDarkTheme = isDarkTheme;
    }
    Player currentPlayer = player;
    private char[][] board = new char[3][3];

    private boolean gameOver = false;
    private int playerScore = 0;
    private int computerScore = 0;
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
                button.setFont(Font.font("Arial", FontWeight.BOLD, 48));
                int finalI = i;
                int finalJ = j;

                button.setOnAction(event -> {
                    if (!gameOver && board[finalI][finalJ] == '\u0000') {
                        button.setText(String.valueOf(currentPlayer.getSign()));
                        board[finalI][finalJ] = currentPlayer.getSign();
                        if(isDarkTheme){
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
                                board[bestMoveI][bestMoveJ] = currentPlayer.getSign();
                                buttons[bestMoveI][bestMoveJ].setText(String.valueOf(currentPlayer.getSign()));
                                if(isDarkTheme){
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
                                }
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
        resetButton.setOnAction(event -> resetGame(buttons));

        Button resetScoreButton = new Button("Обнулення");
        resetScoreButton.setMinWidth(200);
        resetScoreButton.setMinHeight(50);
        resetScoreButton.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        resetScoreButton.setOnAction(event -> {
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

        BorderPane root = new BorderPane();
        root.setTop(infoBox);
        root.setCenter(gameBox);

        Scene scene = new Scene(root, 700, 950);

        if (isDarkTheme) {
            scene.getRoot().setStyle("-fx-background-color: black");
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

        primaryStage.setScene(scene);
        primaryStage.setMinWidth(650);
        primaryStage.setMinHeight(300);
        primaryStage.show();
    }


}


