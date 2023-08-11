package game.tictactoeproject.GameWithAI;
import game.tictactoeproject.Logic.GameState;
import game.tictactoeproject.Logic.GameLogic;
import game.tictactoeproject.Logic.Player;
import javafx.application.*;
import javafx.geometry.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.*;
import javafx.stage.*;
import javafx.animation.*;
import javafx.util.*;
import java.util.*;


public class EasyGame extends Application {
    Player player = new Player("Гравець", 'X');
    Player computer = new Player("Бот", 'O');
    private Scene aiMenuScene;
    private boolean isDarkTheme;
    public EasyGame(Scene aiMenuScene, boolean isDarkTheme) {
        this.aiMenuScene = aiMenuScene;
        this.isDarkTheme = isDarkTheme;
    }
    private char[][] board = new char[3][3];
    Player currentPlayer = player;
    private boolean gameOver = false;
    boolean isBotTurn = false;
    private Label statusLabel = new Label("Твій хід.");
    private Label playerScoreLabel = new Label("Гравець: 0");
    private Label computerScoreLabel = new Label("Бот: 0");
    private int playerScore = 0;

    PauseTransition pause = new PauseTransition(Duration.seconds(3));

    Timeline timeline = new Timeline();
    private int computerScore = 0;
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
                    if (!gameOver && board[finalI][finalJ] == '\u0000' && !isBotTurn) {
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
                            currentPlayer = (currentPlayer == player) ? computer : player;
                            if (currentPlayer.getSign() == 'O') {
                                isBotTurn = true;
                                timeline.getKeyFrames().addAll(
                                        new KeyFrame(Duration.seconds(0), event1 -> statusLabel.setText("Бот думає.")),
                                        new KeyFrame(Duration.seconds(0.5), event1 -> statusLabel.setText("Бот думає..")),
                                        new KeyFrame(Duration.seconds(1), event1 -> statusLabel.setText("Бот думає...")),
                                        new KeyFrame(Duration.seconds(1.5), event1 -> statusLabel.setText("Бот думає."))
                                );
                                timeline.setCycleCount(Animation.INDEFINITE);
                                timeline.play();
                                pause.setOnFinished(event1 -> {
                                    // Знаходження всіх вільних клітинок на дошці
                                    List<int[]> freeCells = new ArrayList<>();
                                    for (int k = 0; k < 3; k++) {
                                        for (int l = 0; l < 3; l++) {
                                            if (board[k][l] == '\u0000') {
                                                freeCells.add(new int[]{k, l});
                                            }
                                        }
                                    }
                                    // Вибір випадкової вільної клітинки
                                    int[] cell = freeCells.get(new Random().nextInt(freeCells.size()));
                                    int bestMoveI = cell[0];
                                    int bestMoveJ = cell[1];
                                    // Робимо хід у випадкову клітинку
                                    board[bestMoveI][bestMoveJ] = currentPlayer.getSign();
                                    buttons[bestMoveI][bestMoveJ].setText(String.valueOf(currentPlayer.getSign()));
                                    timeline.stop();
                                    if (isDarkTheme) {
                                        buttons[bestMoveI][bestMoveJ].setStyle("-fx-text-fill: blue; -fx-background-color: black; -fx-border-color: white;");
                                    }
                                    // Перевірка, чи є переможцем або чи гра закінчилася внічию
                                    if (getGameState(board) == GameState.X_WON || getGameState(board) == GameState.O_WON) {
                                        statusLabel.setText(currentPlayer.getName() + " переміг!");
                                        if (currentPlayer.getSign() == 'X') {
                                            playerScore++;
                                            playerScoreLabel.setText("Гравець: " + playerScore);
                                        } else {
                                            computerScore++;
                                            computerScoreLabel.setText("Бот: " + computerScore);
                                        }
                                        gameOver = true;
                                    } else if (getGameState(board) == GameState.DRAW) {
                                        statusLabel.setText("Нічия!");
                                        gameOver = true;
                                    } else {
                                        currentPlayer = (currentPlayer == player) ? computer : player;
                                        statusLabel.setText("Бот зробив хід. Тепер твоя черга.");
                                        isBotTurn = false;
                                    }
                                });
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
        resetButton.setOnAction(event -> resetGame(buttons));

        Button resetScoreButton = new Button("Обнулення");
        resetScoreButton.setMinWidth(200);
        resetScoreButton.setMinHeight(50);
        resetScoreButton.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        resetScoreButton.setOnAction(event -> {
            playerScore = 0;
            computerScore = 0;
            playerScoreLabel.setText("Гравець: " + playerScore);
            computerScoreLabel.setText("Гравець: " + computerScore);
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