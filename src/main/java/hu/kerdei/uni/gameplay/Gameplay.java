package hu.kerdei.uni.gameplay;

import java.math.*;

import hu.kerdei.uni.Main;
import hu.kerdei.uni.gameobject.*;

import static java.lang.Math.abs;

public class Gameplay {

    public static Board board;
    private Player player1;
    private Player player2;

    public Gameplay(String player1Name, String player2Name, Board board) {
        Gameplay.board = board;
        player1 = new Player(player1Name, Color.BLUE, true);

        player2 = new Player(player2Name, Color.RED, false);
    }

    public Player getPlayer1() {
        return player1;
    }

    public Player getPlayer2() {
        return player2;
    }

    public void makeMove(Player player, Pos fromPos, Pos toPos) {
        switchNextPlayer(player);
        board.switchFields(fromPos, toPos);
    }

    private void endGame() {
        //TODO
    }

    private void switchNextPlayer(Player player) {
        if (player.getColor().getValue() == player1.getColor().getValue()) {
            player1.setNext(false);
            player2.setNext(true);
        } else {
            player1.setNext(true);
            player2.setNext(false);
        }
    }

    public boolean canMakeMove(Player player, Pos fromPos, Pos toPos) {

        Field fromField = board.getBoardFields().get(fromPos.row).get(fromPos.column);
        Field toField = board.getBoardFields().get(toPos.row).get(toPos.column);

        int rowAbs = abs(fromPos.row - toPos.row);
        int columnAbs = abs(fromPos.column - toPos.column);

        return player.isNext() &&
                !fromField.isEmpty() &&
                fromField.getColor().getValue() == player.getColor().getValue() &&
                toField.isEmpty()&&
                rowAbs <= 1 &&
                columnAbs <= 1;
    }


}
