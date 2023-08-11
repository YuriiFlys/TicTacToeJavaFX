package game.tictactoeproject.GameWithAI;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
public class MenuWithAI extends Application {
    private Scene menuScene;
    private boolean isDarkTheme;
    public MenuWithAI(Scene menuScene, boolean isDarkTheme) {
        this.menuScene = menuScene;
        this.isDarkTheme = isDarkTheme;
    }

    @Override
    public void start(Stage primaryStage) {
        Label titleLabel = new Label("Вибери складність:");
        titleLabel.setFont(Font.font("Arial", FontWeight.BOLD, 32));
        Button easyButton = new Button("Легка");
        easyButton.setMinWidth(200);
        easyButton.setMinHeight(50);
        easyButton.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        Button hardButton = new Button("Важка");
        hardButton.setMinWidth(200);
        hardButton.setMinHeight(50);
        hardButton.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        Button backButton = new Button("Назад");
        backButton.setMinWidth(200);
        backButton.setMinHeight(50);
        backButton.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        VBox menuBox = new VBox();
        menuBox.setAlignment(Pos.CENTER);
        menuBox.setSpacing(10);
        menuBox.getChildren().addAll(titleLabel, easyButton, hardButton, backButton);

        Scene aiMenuScene = new Scene(menuBox, 700, 600);

        easyButton.setOnAction(event -> {
            EasyGame game = new EasyGame(aiMenuScene, isDarkTheme);
            game.start(primaryStage);
        });
        hardButton.setOnAction(event -> {
            HardGame game = new HardGame(aiMenuScene, isDarkTheme);
            game.start(primaryStage);
        });
        backButton.setOnAction(event -> {
            primaryStage.setScene(menuScene);
            primaryStage.show();
        });
        if(isDarkTheme){
            aiMenuScene.getRoot().setStyle("-fx-background-color: black");
            titleLabel.setStyle("-fx-text-fill: white;-fx-background-color: black;-fx-border-color: white");
            easyButton.setStyle("-fx-text-fill: white;-fx-background-color: black;-fx-border-color: white");
            hardButton.setStyle("-fx-text-fill: white;-fx-background-color: black;-fx-border-color: white");
            backButton.setStyle("-fx-text-fill: white;-fx-background-color: black;-fx-border-color: white");
        }
        primaryStage.setScene(aiMenuScene);
        primaryStage.show();
    }
}
