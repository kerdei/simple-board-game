package hu.kerdei.uni.test;

import hu.kerdei.uni.gameobject.Board;
import hu.kerdei.uni.gameobject.Color;
import hu.kerdei.uni.gameobject.Field;
import hu.kerdei.uni.gameobject.Pos;
import hu.kerdei.uni.gameplay.Gameplay;
import org.junit.Test;

import static org.junit.Assert.*;

public class GamePlayTest {

    @Test
    public void validMoveBlue(){

        Board board = new Board();

        //Blue field
        Pos fromPos = new Pos(0,0);
        //Empty field
        Pos toPos = new Pos(1,0);

        Gameplay gameplay = new Gameplay("Player1","Player2",board);

        Field pastStateOfField = Gameplay.board.getBoardFields().get(fromPos.row).get(fromPos.column);

        assertEquals(Color.BLUE.getValue(),pastStateOfField.getColor().getValue());
        assertTrue(gameplay.canMakeMove(gameplay.getPlayer1(),fromPos,toPos));
        if (gameplay.canMakeMove(gameplay.getPlayer1(),fromPos,toPos)) {
            gameplay.makeMove(gameplay.getPlayer1(), fromPos, toPos);
        }
        Field newStateOfField = Gameplay.board.getBoardFields().get(toPos.row).get(fromPos.column);
        assertEquals(pastStateOfField,newStateOfField);

    }

    @Test
    public void validMoveRed(){

        Board board = new Board();

        //Moving out the field from the start state
        Pos beforeCheckPos = new Pos(0,1);

        Pos fromPos = new Pos(2,3);
        Pos toPos = new Pos(3, 3);

        //Color.RED field
        board.switchFields(beforeCheckPos, fromPos);

        Gameplay gameplay = new Gameplay("Player1","Player2",board);

        Field pastStateOfTheRedField = Gameplay.board.getBoardFields().get(fromPos.row).get(fromPos.column);

        assertEquals(Color.RED.getValue(),pastStateOfTheRedField.getColor().getValue());

        //Make move with player1 first, after player2 can make a move
        gameplay.makeMove(gameplay.getPlayer1(),new Pos(0,0),new Pos(1,0));

        assertTrue(gameplay.canMakeMove(gameplay.getPlayer2(),fromPos, toPos));

        if ( gameplay.canMakeMove(gameplay.getPlayer2(),fromPos,toPos)){
            gameplay.makeMove(gameplay.getPlayer2(),fromPos,toPos);
        }
        
        Field newStateOfTheRedField = Gameplay.board.getBoardFields().get(toPos.row).get(fromPos.column);
        assertEquals(pastStateOfTheRedField,newStateOfTheRedField);
    }

    @Test
    public void invalidMoveNotAdjacent(){
        //Blue field
        Pos fromPos = new Pos(0,0);
        //Empty field
        Pos toPos = new Pos(2,0);

        Gameplay gameplay = new Gameplay("Player1","Player2",new Board());

        Field pastStateOfField = Gameplay.board.getBoardFields().get(fromPos.row).get(fromPos.column);

        assertEquals(Color.BLUE.getValue(),pastStateOfField.getColor().getValue());
        assertFalse(gameplay.canMakeMove(gameplay.getPlayer1(),fromPos,toPos));
    }

    @Test
    public void invalidMoveNotNextPlayer(){

        //Red field
        Pos fromPos = new Pos(0,1);

        //Empty field
        Pos toPos = new Pos(1,1);


        Gameplay gameplay = new Gameplay("Player1","Player2",new Board());

        assertFalse(gameplay.getPlayer2().isNext());
        assertFalse(gameplay.canMakeMove(gameplay.getPlayer2(),fromPos,toPos));
    }

    @Test
    public void invalidMoveNotOwnColor(){

        //Red field
        Pos fromPos = new Pos(0,1);

        //Empty field
        Pos toPos = new Pos(1,1);


        Gameplay gameplay = new Gameplay("Player1","Player2",new Board());

        Field redField = Gameplay.board.getBoardFields().get(fromPos.row).get(fromPos.column);
        assertNotEquals(gameplay.getPlayer1().getColor().getValue(),redField.getColor().getValue());
        assertFalse(gameplay.canMakeMove(gameplay.getPlayer1(),fromPos,toPos));
    }

    @Test
    public void invalidMoveOnOtherColoredField(){
        //Blue field
        Pos fromPos = new Pos(0,0);

        //Red Field
        Pos toPos = new Pos(0,1);


        Gameplay gameplay = new Gameplay("Player1","Player2",new Board());

        Field blueField = Gameplay.board.getBoardFields().get(fromPos.row).get(fromPos.column);
        Field redField= Gameplay.board.getBoardFields().get(toPos.row).get(toPos.column);

        assertTrue(!blueField.isEmpty());
        assertTrue(!redField.isEmpty());

        assertFalse(gameplay.canMakeMove(gameplay.getPlayer1(),fromPos,toPos));
    }

    @Test
    public void endConditionShouldResultInGameOver(){

    }



}
