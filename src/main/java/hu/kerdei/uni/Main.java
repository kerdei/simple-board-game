package hu.kerdei.uni;

import hu.kerdei.uni.gameobject.Pos;
import hu.kerdei.uni.gameplay.Gameplay;
import hu.kerdei.uni.view.PrintClass;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Scanner;

public class Main {

    public static final Logger LOG = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {

        Gameplay.getInstance();

        PrintClass.startMessage();
        PrintClass.askPlayer1Name();
        Scanner scanner = new Scanner(System.in);
        Gameplay.getInstance().setPlayer1Name(scanner.next());

        PrintClass.askPlayer2Name();
        scanner = new Scanner(System.in);
        Gameplay.getInstance().setPlayer2Name(scanner.next());

        PrintClass.infoAboutStart();
        PrintClass.pressAnyKey();

        //Enter to continue
        try {
            System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }


        while (!Gameplay.getInstance().isGameOver()) {
            PrintClass.printBoard(Gameplay.getInstance().getBoard().toString());

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

        PrintClass.printBoard(Gameplay.getInstance().getBoard().toString());

        PrintClass.endMessage(Gameplay.getInstance().getWinnerPlayer().getName());
        PrintClass.pressAnyKey();

        //Enter to continue
        try {
            System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
