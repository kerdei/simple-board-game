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
            PrintClass.askIPAddress();
            Scanner scanner = new Scanner(System.in);

            GameNetwork gameNetwork = new GameNetwork(scanner.next());

            PrintClass.startMessage();
            PrintClass.askPlayer1Name();
            scanner = new Scanner(System.in);
            Gameplay.getInstance().setPlayer1Name(scanner.next());

            PrintClass.askPlayer2Name();
            scanner = new Scanner(System.in);
            Gameplay.getInstance().setPlayer2Name(scanner.next());

            PrintClass.infoAboutStart();
            PrintClass.pressAnyKey();

            //Enter to continue
            System.in.read();

            Board board = Gameplay.getInstance().getBoard();
            while (!Gameplay.getInstance().isGameOver()) {
                PrintClass.printBoard(board.toString());
                gameNetwork.communicateBoardWithMcu(board.boardfieldsInString());
                PrintClass.nextPlayerShouldMakeMove(Gameplay.getInstance().getNextPlayer());
                scanner = new Scanner(System.in);
                Pos from = new Pos(scanner.nextInt(), scanner.nextInt());
                Pos to = new Pos(scanner.nextInt(), scanner.nextInt());


                if (Gameplay.getInstance().canMakeMove(from, to)) {
                    Gameplay.getInstance().makeMove(from, to);
                    PrintClass.madeMove(from, to);
                } else {
                    PrintClass.wrongMove();
                }
            }

            Gameplay.getInstance().endGame();

            PrintClass.printBoard(board.toString());
            gameNetwork.communicateBoardWithMcu(board.boardfieldsInString());
            PrintClass.endMessage(Gameplay.getInstance().getWinnerPlayer().getName());
            PrintClass.pressAnyKey();

            //Enter to continue
            System.in.read();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
