package hu.kerdei.uni;

import hu.kerdei.uni.gameobject.Board;
import hu.kerdei.uni.gameobject.Pos;
import hu.kerdei.uni.gameplay.Gameplay;
import hu.kerdei.uni.network.GameNetwork;
import hu.kerdei.uni.view.PrintClass;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Scanner;

public class Main {

    public static final Logger LOG = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        try {

            Scanner scanner;

            GameNetwork gameNetwork = null;
            if (args.length > 0 && args[0].equalsIgnoreCase("-A")) {
                PrintClass.askIPAddress();
                scanner = new Scanner(System.in);
                gameNetwork = new GameNetwork(scanner.next());
            }

            PrintClass.startMessage();
            PrintClass.askPlayer1Name();
            scanner = new Scanner(System.in);
            Gameplay gameplay = Gameplay.getInstance();

            gameplay.setPlayer1Name(scanner.nextLine());

            PrintClass.askPlayer2Name();
            scanner = new Scanner(System.in);




            gameplay.setPlayer2Name(scanner.nextLine());

            PrintClass.infoAboutStart();
            PrintClass.pressAnyKey();

            //Enter to continue
            System.in.read();

            Board board = gameplay.getBoard();
            while (!gameplay.isGameOver()) {
                PrintClass.printBoard(board.toString());
                if (gameNetwork != null) {
                    gameNetwork.communicateBoardWithMcu(board.boardfieldsInString());
                }
                PrintClass.nextPlayerShouldMakeMove(gameplay.getNextPlayer());
                scanner = new Scanner(System.in);
                Pos from = new Pos(scanner.nextInt(), scanner.nextInt());
                Pos to = new Pos(scanner.nextInt(), scanner.nextInt());


                if (gameplay.canMakeMove(from, to)) {
                    gameplay.makeMove(from, to);
                    PrintClass.madeMove(from, to);
                } else {
                    PrintClass.wrongMove();
                }
            }

            gameplay.endGame();
            PrintClass.printBoard(board.toString());
            if (gameNetwork != null) {
                gameNetwork.communicateBoardWithMcu(board.boardfieldsInString());
            }
            PrintClass.endMessage(gameplay.getWinnerPlayer().getName());
            PrintClass.pressAnyKey();

            //Enter to continue
            System.in.read();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
