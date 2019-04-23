package hu.kerdei.uni.test;

import hu.kerdei.uni.gameobject.Board;
import hu.kerdei.uni.gameobject.Color;
import hu.kerdei.uni.gameobject.Field;
import hu.kerdei.uni.gameobject.Pos;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class GameObjectsTest {

    private static final Logger LOG = LoggerFactory.getLogger(GameObjectsTest.class);

    public GameObjectsTest() {
        LOG.debug("GameObjectsTest start");
    }

    @Test
    public void boardConstructorShouldGiveBackTheStartState() {

        LOG.debug("Start Board Constructor Test");
        /*
        Init board manually with the starter state.
         */

        ArrayList<ArrayList<Field>> boardFieldsWithStarterState = new ArrayList<>();

        ArrayList<Field> row = new ArrayList<Field>();

        row.add(new Field(Color.BLUE));
        row.add(new Field(Color.RED));
        row.add(new Field(Color.BLUE));
        row.add(new Field(Color.RED));
        boardFieldsWithStarterState.add(row);

        row = new ArrayList<>();
        row.add(new Field(true, null));
        row.add(new Field(true, null));
        row.add(new Field(true, null));
        row.add(new Field(true, null));
        boardFieldsWithStarterState.add(row);


        row = new ArrayList<>();
        row.add(new Field(true, null));
        row.add(new Field(true, null));
        row.add(new Field(true, null));
        row.add(new Field(true, null));
        boardFieldsWithStarterState.add(row);


        row = new ArrayList<>();
        row.add(new Field(true, null));
        row.add(new Field(true, null));
        row.add(new Field(true, null));
        row.add(new Field(true, null));
        boardFieldsWithStarterState.add(row);

        row = new ArrayList<>();
        row.add(new Field(Color.RED));
        row.add(new Field(Color.BLUE));
        row.add(new Field(Color.RED));
        row.add(new Field(Color.BLUE));
        boardFieldsWithStarterState.add(row);

        Board boardWithStarterState = new Board(boardFieldsWithStarterState);

        Board boardInitedWithConstructor = new Board();

        LOG.debug(boardWithStarterState.toString());
        LOG.debug(boardInitedWithConstructor.toString());
        assertEquals(boardWithStarterState, boardInitedWithConstructor);
        LOG.debug("End Board Constructor Test");
    }

    @Test
    public void emptyFieldSwitchTest() {
        LOG.debug("Start of emptyFieldSwitchTest");
        Board board = new Board();

        Pos fromPos = new Pos(3, 2);
        Pos toPos = new Pos(3, 3);

        Field fromField = board.getBoardField(fromPos);
        Field toField = board.getBoardField(toPos);

        assertTrue(fromField.isEmpty());
        assertTrue(toField.isEmpty());

        assertNull(fromField.getColor());
        assertNull(toField.getColor());

        board.switchFields(fromPos, toPos);

        assertTrue(fromField.isEmpty());
        assertTrue(toField.isEmpty());

        assertNull(fromField.getColor());
        assertNull(toField.getColor());

        assertEquals(fromField, board.getBoardField(toPos));
        assertEquals(toField, board.getBoardField(fromPos));
    }

    @Test
    public void switchingEmptyWithNonemptyField() {
        LOG.debug("Start of switchingEmptyWithNonemptyField");

        Board board = new Board();

        Pos fromPos = new Pos(0, 0);
        Pos toPos = new Pos(1, 0);

        Field emptyField = board.getBoardField(toPos);
        Field blueField = board.getBoardField(fromPos);

        assertTrue(emptyField.isEmpty());
        assertFalse(blueField.isEmpty());
        assertEquals(blueField.getColor(), Color.BLUE);

        assertNotEquals(emptyField, blueField);

        board.switchFields(fromPos, toPos);

        assertEquals(emptyField, board.getBoardField(fromPos));
        assertEquals(blueField, board.getBoardField(toPos));

    }

    @Test
    public void switchingTwoNonEmptyField() {
        LOG.debug("Start of switchingTwoNonEmptyField");

        Board board = new Board();

        Pos bluePos = new Pos(0, 0);
        Pos redPos = new Pos(0, 1);

        Field blueField = board.getBoardField(bluePos);
        Field redField = board.getBoardField(redPos);

        assertFalse(redField.isEmpty());
        assertEquals(redField.getColor(), Color.RED);

        assertFalse(blueField.isEmpty());
        assertEquals(blueField.getColor(), Color.BLUE);

        //The Red and the Blue field switched positions
        board.switchFields(bluePos,redPos);

        assertEquals(blueField,board.getBoardField(redPos));
        assertEquals(redField,board.getBoardField(bluePos));
    }

    @Test
    public void verticalIsFinalStateTest1() {
        LOG.debug("Vertical test #1");
        Board board = new Board();
        board.switchFields(new Pos(0, 0), new Pos(1, 2));
        board.switchFields(new Pos(0, 2), new Pos(1, 3));
        board.switchFields(new Pos(4, 1), new Pos(1, 1));
        LOG.debug(board.toString());
        assertEquals(Color.BLUE, board.getBoardFields().get(1).get(1).getColor());
        assertEquals(Color.BLUE, board.getBoardFields().get(1).get(2).getColor());
        assertEquals(Color.BLUE, board.getBoardFields().get(1).get(3).getColor());
        assertTrue(board.isFinalState());
    }

    @Test
    public void verticalIsFinalStateTest2() {

        LOG.debug("Vertical test #2");
        Board board = new Board();
        board.switchFields(new Pos(4, 0), new Pos(2, 2));
        board.switchFields(new Pos(4, 2), new Pos(2, 3));
        board.switchFields(new Pos(0, 1), new Pos(2, 1));

        LOG.debug(board.toString());
        assertEquals(Color.RED, board.getBoardFields().get(2).get(1).getColor());
        assertEquals(Color.RED, board.getBoardFields().get(2).get(2).getColor());
        assertEquals(Color.RED, board.getBoardFields().get(2).get(3).getColor());
        assertTrue(board.isFinalState());
    }

    @Test
    public void horizontalIsFinalStateTest1() {
        LOG.debug("Horizontal test #1");
        Board board = new Board();
        board.switchFields(new Pos(0, 0), new Pos(1, 1));
        board.switchFields(new Pos(0, 2), new Pos(2, 1));
        board.switchFields(new Pos(4, 1), new Pos(3, 1));
        LOG.debug(board.toString());
        assertEquals(Color.BLUE, board.getBoardFields().get(1).get(1).getColor());
        assertEquals(Color.BLUE, board.getBoardFields().get(2).get(1).getColor());
        assertEquals(Color.BLUE, board.getBoardFields().get(3).get(1).getColor());
        assertTrue(board.isFinalState());
    }

    @Test
    public void horizontalIsFinalStateTest2() {

        LOG.debug("Horizontal test #2");
        Board board = new Board();
        board.switchFields(new Pos(4, 0), new Pos(1, 3));
        board.switchFields(new Pos(4, 2), new Pos(2, 3));
        board.switchFields(new Pos(0, 1), new Pos(3, 3));

        LOG.debug(board.toString());
        assertEquals(Color.RED, board.getBoardFields().get(1).get(3).getColor());
        assertEquals(Color.RED, board.getBoardFields().get(2).get(3).getColor());
        assertEquals(Color.RED, board.getBoardFields().get(3).get(3).getColor());
        assertTrue(board.isFinalState());
    }

    @Test
    public void crossIsFinalStateTest1() {

        LOG.debug("Cross test #1");
        Board board = new Board();
        board.switchFields(new Pos(0, 0), new Pos(1, 1));
        board.switchFields(new Pos(0, 2), new Pos(2, 2));
        board.switchFields(new Pos(4, 1), new Pos(3, 3));

        LOG.debug(board.toString());
        assertEquals(Color.BLUE, board.getBoardFields().get(1).get(1).getColor());
        assertEquals(Color.BLUE, board.getBoardFields().get(2).get(2).getColor());
        assertEquals(Color.BLUE, board.getBoardFields().get(3).get(3).getColor());
        assertTrue(board.isFinalState());
    }


    @Test
    public void crossIsFinalStateTest2() {
        LOG.debug("Cross test #2");
        Board board = new Board();
        board.switchFields(new Pos(4, 0), new Pos(3, 0));
        board.switchFields(new Pos(4, 2), new Pos(2, 1));
        board.switchFields(new Pos(0, 1), new Pos(1, 2));

        LOG.debug(board.toString());
        assertEquals(Color.RED, board.getBoardFields().get(3).get(0).getColor());
        assertEquals(Color.RED, board.getBoardFields().get(2).get(1).getColor());
        assertEquals(Color.RED, board.getBoardFields().get(1).get(2).getColor());
        assertTrue(board.isFinalState());
    }


    @Test
    public void startStateIsNotAFinalStateTest() {
        LOG.debug("startStateIsNotAFinalStateTest");
        Board board = new Board();
        assertFalse(board.isFinalState());
    }

    @Test
    public void notFinalState1Test1() {
        LOG.debug("notFinalState1Test1");
        Board board = new Board();
        board.switchFields(new Pos(4, 0), new Pos(3, 0));
        board.switchFields(new Pos(4, 2), new Pos(2, 1));
        board.switchFields(new Pos(0, 1), new Pos(1, 3));
        assertFalse(board.isFinalState());
    }

    @Test
    public void notFinalState1Test2() {
        LOG.debug("notFinalState1Test2");
        Board board = new Board();
        board.switchFields(new Pos(0, 0), new Pos(2, 1));
        board.switchFields(new Pos(0, 2), new Pos(2, 2));
        board.switchFields(new Pos(4, 1), new Pos(1, 3));
        assertFalse(board.isFinalState());
    }

    @Test
    public void notFinalState1Test3() {
        LOG.debug("notFinalState1Test3");
        Board board = new Board();
        board.switchFields(new Pos(0, 1), new Pos(1, 1));
        board.switchFields(new Pos(1, 2), new Pos(2, 2));
        board.switchFields(new Pos(2, 3), new Pos(3, 2));
        board.switchFields(new Pos(1, 1), new Pos(1, 3));
        board.switchFields(new Pos(2, 2), new Pos(2, 3));
        board.switchFields(new Pos(3, 3), new Pos(2, 3));
        board.switchFields(new Pos(1, 1), new Pos(2, 2));
        board.switchFields(new Pos(4, 2), new Pos(1, 1));
        assertFalse(board.isFinalState());
    }

}
