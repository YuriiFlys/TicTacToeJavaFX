package game.tictactoeproject.Logic;

public class Player {
    private final String name;
    private final char sign;

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