package hu.kerdei.uni.gameplay;

import hu.kerdei.uni.gameobject.*;

import static java.lang.Math.abs;

public class Gameplay {

    private static final Gameplay instance = new Gameplay();

    private Board board;
    private Player player1;
    private Player player2;
    private Player winnerPlayer;

    private Gameplay() {
        board = new Board();
        player1 = new Player(Color.BLUE, true);
        player2 = new Player(Color.RED, false);
    }

    static public Gameplay getInstance() {
        return instance;
    }

    public void makeMove(Pos fromPos, Pos toPos) {
        switchNextPlayer(getNextPlayer());
        board.switchFields(fromPos, toPos);
    }

    public void resetGameplay() {
        player1.setNext(true);
        player2.setNext(false);
        board = new Board();
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

    public boolean canMakeMove(Pos fromPos, Pos toPos) {

        Player player = getNextPlayer();

        Field fromField = board.getBoardFields().get(fromPos.row).get(fromPos.column);
        Field toField = board.getBoardFields().get(toPos.row).get(toPos.column);

        int rowAbs = abs(fromPos.row - toPos.row);
        int columnAbs = abs(fromPos.column - toPos.column);

        return player.isNext() &&
                !fromField.isEmpty() &&
                fromField.getColor().getValue() == player.getColor().getValue() &&
                toField.isEmpty() &&
                ((rowAbs <= 1 && columnAbs == 0) || rowAbs == 0 && columnAbs <= 1);
    }

    public Player getNextPlayer() {
        return player1.isNext() ? player1 : player2;
    }

    public void endGame() {
        winnerPlayer = player1.isNext() ? player2 : player1;
    }


    public boolean isGameOver() {
        return board.isFinalState();
    }

    public Player getWinnerPlayer() {
        return winnerPlayer;
    }

    public Player getPlayer1() {
        return player1;
    }

    public Player getPlayer2() {
        return player2;
    }

    public void setPlayer1Name(String player1Name) {
        player1.setName(player1Name);
    }

    public void setPlayer2Name(String player2Name) {
        player2.setName(player2Name);
    }

    public Board getBoard() {
        return board;
    }
}
