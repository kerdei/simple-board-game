package hu.kerdei.uni.test;

import hu.kerdei.uni.gameobject.Board;
import hu.kerdei.uni.gameobject.Pos;
import hu.kerdei.uni.gameplay.Gameplay;
import org.junit.Test;

public class GamePlayTest {

    @Test
    public void validMoves(){

        Board board = new Board();

        Pos fromPos = new Pos(0,0);
        Pos toPos = new Pos(1,0);

        Gameplay gameplay = new Gameplay("Player1","Player2",board);


        if (gameplay.canMakeMove(gameplay.getPlayer1(),fromPos,toPos)) {
            gameplay.makeMove(gameplay.getPlayer1(), fromPos, toPos);
        }

    }

    @Test
    public void invalidMoves(){
        //TODO
    }

    @Test
    public void endConditionShouldResultInGameOver(){
        //TODO
    }



}
