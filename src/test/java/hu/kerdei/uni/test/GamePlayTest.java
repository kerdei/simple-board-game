package hu.kerdei.uni.test;

import hu.kerdei.uni.gameobject.Color;
import hu.kerdei.uni.gameobject.Field;
import hu.kerdei.uni.gameobject.Pos;
import hu.kerdei.uni.gameplay.Gameplay;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.Assert.*;

public class GamePlayTest {

    private static final Logger LOG = LoggerFactory.getLogger(GamePlayTest.class);


    public GamePlayTest() {
        LOG.debug("GamePlayTest start");
    }

    @Test
    public void validMoveBlue() {
        LOG.debug("validMoveBlue");

        //Blue field
        Pos fromPos = new Pos(0, 0);
        //Empty field
        Pos toPos = new Pos(1, 0);

        Gameplay gameplay = Gameplay.getInstance();
        gameplay.resetGameplay();

        Field pastStateOfField = gameplay.getBoard().getBoardFields().get(fromPos.row).get(fromPos.column);

        assertEquals(Color.BLUE.getValue(), pastStateOfField.getColor().getValue());
        assertTrue(gameplay.canMakeMove(fromPos, toPos));
        if (gameplay.canMakeMove(fromPos, toPos)) {
            gameplay.makeMove(fromPos, toPos);
        }
        Field newStateOfField = gameplay.getBoard().getBoardFields().get(toPos.row).get(fromPos.column);
        assertEquals(pastStateOfField, newStateOfField);

    }

    @Test
    public void validMoveRed() {
        LOG.debug("validMoveRed");

        Gameplay gameplay = Gameplay.getInstance();
        gameplay.resetGameplay();

        //Moving out the field from the start state
        Pos beforeCheckPos = new Pos(0, 1);

        Pos fromPos = new Pos(2, 3);
        Pos toPos = new Pos(3, 3);

        //Color.RED field
        gameplay.getBoard().switchFields(beforeCheckPos,fromPos);

        Field pastStateOfTheRedField = gameplay.getBoard().getBoardFields().get(fromPos.row).get(fromPos.column);

        assertEquals(Color.RED.getValue(), pastStateOfTheRedField.getColor().getValue());

        //Make move with player1 first, after player2 can make a move
        gameplay.makeMove(new Pos(0, 0), new Pos(1, 0));
        assertTrue(gameplay.canMakeMove(fromPos, toPos));

        if (gameplay.canMakeMove(fromPos, toPos)) {
            gameplay.makeMove(fromPos, toPos);
        }

        Field newStateOfTheRedField = gameplay.getBoard().getBoardFields().get(toPos.row).get(fromPos.column);
        assertEquals(pastStateOfTheRedField, newStateOfTheRedField);
    }

    @Test
    public void invalidMoveNotAdjacent() {
        LOG.debug("invalidMoveNotAdjacent");

        //Blue field
        Pos fromPos = new Pos(0, 0);
        //Empty field
        Pos toPos = new Pos(2, 0);

        Gameplay gameplay = Gameplay.getInstance();
        gameplay.resetGameplay();

        Field pastStateOfField = gameplay.getBoard().getBoardFields().get(fromPos.row).get(fromPos.column);

        assertEquals(Color.BLUE.getValue(), pastStateOfField.getColor().getValue());
        assertFalse(gameplay.canMakeMove(fromPos, toPos));
    }

    @Test
    public void invalidMoveNotNextPlayer() {
        LOG.debug("invalidMoveNotNextPlayer");

        //Red field
        Pos fromPos = new Pos(0, 1);

        //Empty field
        Pos toPos = new Pos(1, 1);


        Gameplay gameplay = Gameplay.getInstance();
        gameplay.resetGameplay();

        assertFalse(gameplay.getPlayer2().isNext());
        assertFalse(gameplay.canMakeMove(fromPos, toPos));
    }

    @Test
    public void invalidMoveNotOwnColor() {
        LOG.debug("invalidMoveNotOwnColor");

        //Red field
        Pos fromPos = new Pos(0, 1);

        //Empty field
        Pos toPos = new Pos(1, 1);


        Gameplay gameplay = Gameplay.getInstance();
        gameplay.resetGameplay();

        Field redField = gameplay.getBoard().getBoardFields().get(fromPos.row).get(fromPos.column);
        assertNotEquals(gameplay.getPlayer1().getColor().getValue(), redField.getColor().getValue());
        assertFalse(gameplay.canMakeMove(fromPos, toPos));
    }

    @Test
    public void invalidMoveOnOtherColoredField() {
        LOG.debug("invalidMoveOnOtherColoredField");
        //Blue field
        Pos fromPos = new Pos(0, 0);

        //Red Field
        Pos toPos = new Pos(0, 1);


        Gameplay gameplay = Gameplay.getInstance();
        gameplay.resetGameplay();

        Field blueField = gameplay.getBoard().getBoardFields().get(fromPos.row).get(fromPos.column);
        Field redField = gameplay.getBoard().getBoardFields().get(toPos.row).get(toPos.column);

        assertTrue(!blueField.isEmpty());
        assertTrue(!redField.isEmpty());

        assertFalse(gameplay.canMakeMove(fromPos, toPos));
    }

    @Test
    public void invalidCrossMove() {
        LOG.debug("invalidCrossMove");
        //Blue field
        Pos fromPos = new Pos(0, 0);

        //Empty Field
        Pos toPos = new Pos(1, 1);


        Gameplay gameplay = Gameplay.getInstance();
        gameplay.resetGameplay();

        Field blueField = gameplay.getBoard().getBoardFields().get(fromPos.row).get(fromPos.column);
        Field emptyField = gameplay.getBoard().getBoardFields().get(toPos.row).get(toPos.column);

        assertTrue(!blueField.isEmpty());
        assertTrue(emptyField.isEmpty());

        assertFalse(gameplay.canMakeMove(fromPos, toPos));
    }
}
