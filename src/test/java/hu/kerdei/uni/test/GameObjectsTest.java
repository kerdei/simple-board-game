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

    public GameObjectsTest(){
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
    public void switchingFieldsTest() {
        LOG.debug("Start of switchingFieldsTest");

        Board board = new Board();

        /*
        #1 Switching two empty fields
         */

        Pos fromPos = new Pos(3, 2);
        Pos toPos = new Pos(3, 3);

        Field pastStateOfTheEmptyField1 = board.getBoardFields().get(fromPos.row).get(fromPos.column);
        Field pastStateOfTheEmptyField2 = board.getBoardFields().get(toPos.row).get(toPos.column);

        assertNotEquals(pastStateOfTheEmptyField1, pastStateOfTheEmptyField2);

        board.switchFields(fromPos, toPos);

        Field newStateOfTheEmptyField1 = board.getBoardFields().get(toPos.row).get(toPos.column);
        Field newStateOfTheEmptyField2 = board.getBoardFields().get(fromPos.row).get(fromPos.column);

        LOG.debug("Each of the empty field objects should equal to the pastSelf");
        LOG.debug(pastStateOfTheEmptyField1.toString());
        LOG.debug(pastStateOfTheEmptyField2.toString());
        assertEquals(pastStateOfTheEmptyField1, newStateOfTheEmptyField1);
        assertEquals(pastStateOfTheEmptyField2, newStateOfTheEmptyField2);

        /*
        #2 Switching an empty field, with a non empty one should give back the same objects on the switched fields.
         */

        fromPos = new Pos(0, 0);
        toPos = new Pos(1, 0);

        Field pastStateOfNonEmptyField = board.getBoardFields().get(fromPos.row).get(fromPos.column);
        Field pastStateOfTheEmptyField = board.getBoardFields().get(toPos.row).get(toPos.column);

        assertNotEquals(pastStateOfNonEmptyField, pastStateOfTheEmptyField);

        board.switchFields(fromPos, toPos);

        Field newStateOftheNonEmptyField = board.getBoardFields().get(toPos.row).get(toPos.column);
        Field newStateOfTheEmptyField = board.getBoardFields().get(fromPos.row).get(fromPos.column);

        LOG.debug("The Non empty field objects should be eq.");
        LOG.debug(pastStateOfNonEmptyField.toString());
        assertEquals(pastStateOfNonEmptyField, newStateOftheNonEmptyField);

        LOG.debug("The empty fields should be eq as well");
        LOG.debug(pastStateOfTheEmptyField.toString());
        assertEquals(pastStateOfTheEmptyField, newStateOfTheEmptyField);


        /*
        #3 Switching two non empty fields.
         */

        fromPos = new Pos(0, 3);
        toPos = new Pos(0, 1);

        Field pastStateOfNonEmptyField1 = board.getBoardFields().get(fromPos.row).get(fromPos.column);
        Field pastStateOfNonEmptyField2 = board.getBoardFields().get(toPos.row).get(toPos.column);


        assertNotEquals(pastStateOfNonEmptyField1, pastStateOfNonEmptyField2);

        board.switchFields(fromPos, toPos);

        Field newStateOftheNonEmptyField1 = board.getBoardFields().get(toPos.row).get(toPos.column);
        Field newStateOftheNonEmptyField2 = board.getBoardFields().get(fromPos.row).get(fromPos.column);

        LOG.debug("Both non empty field's object should be the same with the past self");
        LOG.debug(pastStateOfNonEmptyField1.toString());
        LOG.debug(pastStateOfNonEmptyField2.toString());
        assertEquals(pastStateOfNonEmptyField1, newStateOftheNonEmptyField1);
        assertEquals(pastStateOfNonEmptyField2, newStateOftheNonEmptyField2);
        LOG.debug("End of switchingFieldsTest");
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
