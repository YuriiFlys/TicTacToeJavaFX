module game.tictactoeproject {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;

    opens game.tictactoeproject to javafx.fxml;
    exports game.tictactoeproject;
}
