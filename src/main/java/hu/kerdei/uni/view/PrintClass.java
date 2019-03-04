package hu.kerdei.uni.view;

import hu.kerdei.uni.gameobject.Player;
import hu.kerdei.uni.gameobject.Pos;

public class PrintClass {

    public static void endMessage(String winnerPlayerName) {
        System.out.println("==========================");
        System.out.println("=========GAME OVER========");
        System.out.println("==========================");
        System.out.println();
        System.out.println("WINNER PLAYER:" + winnerPlayerName);
    }

    public static void startMessage() {
        System.out.println("Welcome to this simple board game"); //TODO
    }

    public static void askPlayer1Name() {
        System.out.println();
        System.out.println("First player, your color will be blue");
        System.out.println("Please tell me your name");
        System.out.println("Player1:");
        System.out.println();
    }

    public static void askPlayer2Name() {
        System.out.println();
        System.out.println("Second player, your color will be red");
        System.out.println("Please tell me your name");
        System.out.println("Player2:");
        System.out.println();
    }

    public static void infoAboutStart() {

        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println("The game about to start, here is some info:");
        System.out.println("You can move your fields if you give the ''from'' positions and the ''to'' positions");
        System.out.println("For example input : 0 0 1 0 , will result the (0,0) BLUE field will move to the (0,1) empty field");
        System.out.println();
    }

    public static void nextPlayerShouldMakeMove(Player player) {
        System.out.println("Please give your next move, " + player.getName() + " (" + player.getColor().name() + ")");
    }

    public static void madeMove(Pos from, Pos to) {
        System.out.println("You made a move from (" + from.row + "," + from.column + ") to (" + to.row + "," + to.column + ")");
    }

    public static void pressAnyKey() {
        System.out.println("Press Enter to continue...");
    }

    public static void printBoard(String boardInReadableFormat) {
        System.out.println(boardInReadableFormat);
    }

    public static void wrongMove() {
        System.out.println("This move is not allowed");
    }

    public static void askIPAddress() {
        System.out.println("Please give the ip address of the nodeMCU");
    }
}
