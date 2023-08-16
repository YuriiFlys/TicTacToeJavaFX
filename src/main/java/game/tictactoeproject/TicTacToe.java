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

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

;
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
    public void showLeaderboardPlayers() {
        Map<String, Integer> wins = new HashMap<>();
        Map<String, Integer> games = new HashMap<>();

        try (BufferedReader reader = new BufferedReader(new FileReader("game_results_vs_player.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(" ");
                if (parts[0].equals("Draw")) {
                    String player1 = parts[2];
                    String player2 = parts[4];
                    games.put(player1, games.getOrDefault(player1, 0) + 1);
                    games.put(player2, games.getOrDefault(player2, 0) + 1);
                } else {
                    String winner = parts[0];
                    String loser = parts[5];
                    wins.put(winner, wins.getOrDefault(winner, 0) + 1);
                    games.put(winner, games.getOrDefault(winner, 0) + 1);
                    games.put(loser, games.getOrDefault(loser, 0) + 1);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        List<Map.Entry<String, Double>> leaderboard = new ArrayList<>();
        for (Map.Entry<String, Integer> entry : games.entrySet()) {
            String player = entry.getKey();
            int winCount = wins.getOrDefault(player, 0);
            int gameCount = entry.getValue();
            double winRate = (double) winCount / gameCount;
            leaderboard.add(new AbstractMap.SimpleEntry<>(player, winRate));
        }

        leaderboard.sort((a, b) -> -Double.compare(a.getValue(), b.getValue()));

        System.out.println("Список лідерів:");
        for (Map.Entry<String, Double> entry : leaderboard) {
            String player = entry.getKey();
            double winRate = entry.getValue();
            int winCount = wins.getOrDefault(player, 0);
            int gameCount = games.get(player);
            System.out.println(player + ": " + gameCount + " ігр зіграно, " + winCount + " перемог,"+ String.format("%.2f", winRate * 100) + "% вінрейт");
        }
    }
    public void showLeaderboardBot() {
        Map<String, Integer> wins = new HashMap<>();
        Map<String, Integer> games = new HashMap<>();

        try (BufferedReader reader = new BufferedReader(new FileReader("game_results_vs_computer.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(" ");
                String winner = parts[0];
                if (!winner.equals("Бот") && !winner.equals("Draw")) {
                    wins.put(winner, wins.getOrDefault(winner, 0) + 1);
                    games.put(winner, games.getOrDefault(winner, 0) + 1);
                }
                if(winner.equals("Бот") && !winner.equals("Draw")) {
                    String loser = parts[5];
                    games.put(loser, games.getOrDefault(loser, 0) + 1);
                }
                if (winner.equals("Draw")) {
                    String loser = parts[2];
                    games.put(loser, games.getOrDefault(loser, 0) + 1);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        List<Map.Entry<String, Double>> leaderboard1 = new ArrayList<>();
        for (Map.Entry<String, Integer> entry : games.entrySet()) {
            String player = entry.getKey();
            int winCount = wins.getOrDefault(player, 0);
            int gameCount = entry.getValue();
            double winRate = (double) winCount / gameCount;
            leaderboard1.add(new AbstractMap.SimpleEntry<>(player, winRate));
        }

        leaderboard1.sort((a, b) -> -Double.compare(a.getValue(), b.getValue()));

        System.out.println("Список лідерів:");
        for (Map.Entry<String, Double> entry : leaderboard1) {
            String player = entry.getKey();
            double winRate = entry.getValue();
            int winCount = wins.getOrDefault(player, 0);
            int gameCount = games.get(player);
            System.out.println(player + ": " + gameCount + " ігр зіграно, " + winCount + " перемог,"+ String.format("%.2f", winRate * 100) + "% вінрейт");
        }
    }

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
        Button leaderboardButton = new Button("Список лідерів");
        leaderboardButton.setMinWidth(200);
        leaderboardButton.setMinHeight(50);
        leaderboardButton.setFont(Font.font("Arial", FontWeight.BOLD, 22));
        leaderboardButton.setOnAction(event -> {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Виберіть список лідерів:");
            System.out.println("[1] Список лідерів проти гравців");
            System.out.println("[2] Список лідерів проти бота");

            int choice = scanner.nextInt();
            if (choice == 1) {
                showLeaderboardPlayers();
            } else if (choice == 2) {
                showLeaderboardBot();
            } else {
                System.out.println("Неправильно введено число");
            }

        });
        Label projectLabel = new Label("Project by Yurii Flys");
        projectLabel.setFont(Font.font("Arial", FontWeight.BOLD, 15));
        projectLabel.setStyle("-fx-text-fill: black");
        projectLabel.setEffect(shadow);
        VBox menuBox = new VBox();
        menuBox.setAlignment(Pos.CENTER);
        menuBox.setSpacing(10);
        menuBox.getChildren().addAll(titleLabel, playWithAIButton,playWithFriend, themeButton,leaderboardButton,projectLabel);

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
                leaderboardButton.setStyle("-fx-text-fill: black;");
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
                leaderboardButton.setStyle("-fx-text-fill: white;-fx-background-color: black;-fx-border-color: white");
                isDarkTheme = true;
            }
        });
        playWithAIButton.setEffect(shadow);
        playWithFriend.setEffect(shadow);
        themeButton.setEffect(shadow);
        leaderboardButton.setEffect(shadow);
        primaryStage.setScene(menuScene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}




