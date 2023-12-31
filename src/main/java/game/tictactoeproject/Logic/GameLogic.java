package game.tictactoeproject.Logic;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class GameLogic {


    public static boolean checkWinner(char[][] board) {
        for (int i = 0; i < 3; i++) {
            if (board[i][0] == board[i][1] && board[i][1] == board[i][2] && board[i][0] != '\u0000') {
                return true;
            }
            if (board[0][i] == board[1][i] && board[1][i] == board[2][i] && board[0][i] != '\u0000') {
                return true;
            }
        }
        if (board[0][0] == board[1][1] && board[1][1] == board[2][2] && board[0][0] != '\u0000') {
            return true;
        }

        if(board[0][2] == board[1][1] && board[1][1] == board[2][0] && board[0][2] != '\u0000'){
            return true;
        }
        return false;
    }
    public static boolean isDraw(char[][] board) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == '\u0000') {
                    return false;
                }
            }
        }
        return true;
    }
    public static Button createButton(String text, EventHandler<ActionEvent> eventHandler) {
        Button button = new Button(text);
        button.setMinWidth(200);
        button.setMinHeight(50);
        button.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        button.setOnAction(eventHandler);
        return button;
    }

}