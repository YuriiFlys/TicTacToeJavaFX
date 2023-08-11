module game.tictactoeproject {
    requires javafx.controls;
    requires javafx.fxml;


    opens game.tictactoeproject to javafx.fxml;
    exports game.tictactoeproject;
}