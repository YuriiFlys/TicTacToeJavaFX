package game.tictactoeproject.Logic;

public class Player {
    private String name;
    private char sign;

    public Player(String name, char sign) {
        this.name = name;
        this.sign = sign;
    }

    public String getName() {
        return name;
    }

    public char getSign() {
        return sign;
    }

}