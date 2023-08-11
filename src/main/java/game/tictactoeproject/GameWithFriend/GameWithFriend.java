package game.tictactoeproject.GameWithFriend;
import game.tictactoeproject.Logic.GameLogic;
import game.tictactoeproject.Logic.GameState;
import game.tictactoeproject.Logic.Player;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;


public class GameWithFriend extends Application {
    private Scene aiMenuScene;
    private boolean isDarkTheme;
    public GameWithFriend(Scene aiMenuScene, boolean isDarkTheme) {
        this.aiMenuScene = aiMenuScene;
        this.isDarkTheme = isDarkTheme;
    }
    private char[][] board = new char[3][3];
    Player player1 = new Player("Гравець 1", 'X');
    Player player2 = new Player("Гравець 2", 'O');
    Player currentPlayer = player1;
    private boolean gameOver = false;

    private Label turnLabel = new Label(currentPlayer.getName() + ", твій хід.");
    private Label player1ScoreLabel = new Label("Гравець 1: 0");
    private Label player2ScoreLabel = new Label("Гравець 2: 0");
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
                button.setFont(Font.font("Arial", FontWeight.BOLD, 48));
                int finalI = i;
                int finalJ = j;
                button.setOnAction(event -> {
                    if (!gameOver && board[finalI][finalJ] == '\u0000') {
                        button.setText(String.valueOf(currentPlayer.getSign()));
                        board[finalI][finalJ] = currentPlayer.getSign();
                        if(isDarkTheme && currentPlayer.getSign() == 'X'){
                            button.setStyle("-fx-text-fill: red;-fx-background-color: black;-fx-border-color: white");
                        }
                        else if(isDarkTheme && currentPlayer.getSign() == 'O'){
                            button.setStyle("-fx-text-fill: blue;-fx-background-color: black;-fx-border-color: white");
                        }

                        if (getGameState(board) == GameState.X_WON) {
                            turnLabel.setText(currentPlayer.getName() + " переміг!");
                            if (currentPlayer.getSign() == 'X') {
                                player1Score++;
                                player1ScoreLabel.setText("Гравець 1: " + player1Score);

                            } else if (getGameState(board) == GameState.O_WON) {
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
        resetButton.setOnAction(event -> resetGame(buttons));

        Button resetScoreButton = new Button("Обнулення");
        resetScoreButton.setMinWidth(200);
        resetScoreButton.setMinHeight(50);
        resetScoreButton.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        resetScoreButton.setOnAction(event -> {
            player1Score = 0;
            player2Score = 0;
            player1ScoreLabel.setText("Player 1: " + player1Score);
            player2ScoreLabel.setText("Player 2: " + player2Score);
        });

        Button backButton = new Button("Назад");
        backButton.setMinWidth(200);
        backButton.setMinHeight(50);
        backButton.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        backButton.setOnAction(event -> {
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

        BorderPane root = new BorderPane();
        root.setTop(infoBox);
        root.setCenter(gameBox);
        Scene scene = new Scene(root, 700, 950);
        if (isDarkTheme) {
            scene.getRoot().setStyle("-fx-background-color: black");
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


        primaryStage.setScene(scene);
        primaryStage.setMinWidth(650);
        primaryStage.setMinHeight(300);
        primaryStage.show();
    }

}