package hu.kerdei.uni.gameobject;

import hu.kerdei.uni.Main;

import java.util.ArrayList;
import java.util.Objects;


public class Board {

    private ArrayList<ArrayList<Field>> boardFields;

    public Board() {
        boardFields = new ArrayList<>();

        ArrayList<Field> row = new ArrayList<>();

        row.add(new Field(Color.BLUE));
        row.add(new Field(Color.RED));
        row.add(new Field(Color.BLUE));
        row.add(new Field(Color.RED));
        boardFields.add(row);

        row = new ArrayList<>();
        row.add(new Field(true, null));
        row.add(new Field(true, null));
        row.add(new Field(true, null));
        row.add(new Field(true, null));
        boardFields.add(row);

        row = new ArrayList<>();
        row.add(new Field(true, null));
        row.add(new Field(true, null));
        row.add(new Field(true, null));
        row.add(new Field(true, null));
        boardFields.add(row);


        row = new ArrayList<>();
        row.add(new Field(true, null));
        row.add(new Field(true, null));
        row.add(new Field(true, null));
        row.add(new Field(true, null));
        boardFields.add(row);

        row = new ArrayList<>();
        row.add(new Field(Color.RED));
        row.add(new Field(Color.BLUE));
        row.add(new Field(Color.RED));
        row.add(new Field(Color.BLUE));
        boardFields.add(row);
    }

    public Board(ArrayList<ArrayList<Field>> boardFields) {
        this.boardFields = boardFields;
    }

    public ArrayList<ArrayList<Field>> getBoardFields() {
        return boardFields;
    }

    public Field getBoardField(Pos pos) {
        return boardFields.get(pos.row).get(pos.column);
    }

    public void switchFields(Pos fromPos, Pos toPos) {


        Field field1 = boardFields.get(fromPos.row).get(fromPos.column);
        Field field2 = boardFields.get(toPos.row).get(toPos.column);


        boardFields.get(toPos.row).set(toPos.column, field1);
        Main.LOG.debug(boardFields.get(toPos.row).get(toPos.column).toString());
        boardFields.get(fromPos.row).set(fromPos.column, field2);
        Main.LOG.debug(boardFields.get(fromPos.row).get(fromPos.column).toString());

    }

    @Override
    public boolean equals(Object board) {
        if (this == board) return true;
        if (!(board instanceof Board)) return false;

        ArrayList<ArrayList<Field>> boardFields = ((Board) board).getBoardFields();

        if (boardFields.size() != this.boardFields.size()) {
            return false;
        } else {
            for (int i = 0; i < this.boardFields.size(); i++) {

                ArrayList<Field> boardRowThis = this.boardFields.get(i);
                ArrayList<Field> boardRow = boardFields.get(i);

                for (int j = 0; j < boardRowThis.size(); j++) {

                    /*
                    If one of them empty but the other is not returns false
                     */

                    if (boardRowThis.get(j).isEmpty() && !boardRow.get(j).isEmpty()
                            || !boardRowThis.get(j).isEmpty() && boardRow.get(j).isEmpty()) {
                        return false;
                    }

                    /*
                    If fields are not empty but the colors doesn't match returns false
                     */

                    if (!boardRowThis.get(j).isEmpty() && !boardRow.get(j).isEmpty() && (
                            boardRowThis.get(j).getColor().getValue() != boardRow.get(j).getColor().getValue())) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    @Override
    public int hashCode() {
        int h = 0;
        final int prime = 31;

        for (ArrayList<Field> boardField : boardFields) {
            for (Field field : boardField) {
                h += prime * field.hashCode();
            }
        }
        return h;
    }

    public String boardfieldsInString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 4; j++) {

                if (!boardFields.get(i).get(j).isEmpty()) {
                    String toAppend = boardFields.get(i).get(j).getColor().getValue() == 0 ? "1" : "2";
                    stringBuilder.append(toAppend);
                } else {
                    stringBuilder.append("0");
                }

            }
        }
        return stringBuilder.toString();
    }

    @Override
    public String toString() {


        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("\n");

        for (int i = 0; i < boardFields.size(); i++) {
            if (i == 0) {
                stringBuilder.append("  +-0-+-1-+-2-+-3-+\n");
                stringBuilder.append("  +---+---+---+---+\n");
            }
            for (int j = 0; j < boardFields.get(i).size(); j++) {
                Field actualField = boardFields.get(i).get(j);
                if (j == 0) {
                    stringBuilder.append(i).append(".");
                    stringBuilder.append("| ");
                    if (!actualField.isEmpty()) {
                        if (actualField.getColor().getValue() == Color.BLUE.getValue())
                            stringBuilder.append("B |");
                        else {
                            stringBuilder.append("R |");
                        }
                    } else {
                        stringBuilder.append("  |");
                    }

                } else {
                    if (!actualField.isEmpty()) {

                        if (actualField.getColor().getValue() == Color.BLUE.getValue())
                            stringBuilder.append(" B |");
                        else {
                            stringBuilder.append(" R |");
                        }

                    } else {
                        stringBuilder.append("   |");
                    }
                }
            }
            stringBuilder.append("\n  +---+---+---+---+\n");
        }
        return stringBuilder.toString();
    }

    /*
    Final conditions are the followings:
        #1 There is horizontally 3 fields adjacently with the same color
        #2 There is vertically  3 fields adjacently with the same color
        #3 There is 3 fields diagonally with the same color.
     */
    public boolean isFinalState() {
        //@formatter:off
        // #1
        Main.LOG.debug("isFinalState start");
        Main.LOG.debug("Horizontal check");
        for (ArrayList<Field> boardField : boardFields) {
            for (int j = 1; j < boardField.size() - 1; j++) {
                if (
                        !boardField.get(j - 1).isEmpty() &&
                                !boardField.get(j).isEmpty() &&
                                !boardField.get(j + 1).isEmpty()
                                &&

                                boardField.get(j - 1).getColor().getValue() ==
                                        boardField.get(j).getColor().getValue() &&
                                boardField.get(j + 1).getColor().getValue() ==
                                        boardField.get(j).getColor().getValue() &&
                                boardField.get(j + 1).getColor().getValue() ==
                                        boardField.get(j - 1).getColor().getValue()) {
                    Main.LOG.debug("Horizontal check true at ", boardField.get(j));
                    return true;
                }
            }
        }

        // #2
        Main.LOG.debug("Vertical check");
        for (int i = 1; i < boardFields.size() - 1; i++) {
            for (int j = 0; j < boardFields.get(i).size(); j++) {

                if (
                        !boardFields.get(i - 1).get(j).isEmpty() &&
                                !boardFields.get(i).get(j).isEmpty() &&
                                !boardFields.get(i + 1).get(j).isEmpty() &&

                                boardFields.get(i - 1).get(j).getColor() ==
                                        boardFields.get(i).get(j).getColor() &&
                                boardFields.get(i + 1).get(j).getColor() ==
                                        boardFields.get(i).get(j).getColor() &&
                                boardFields.get(i + 1).get(j).getColor() ==
                                        boardFields.get(i - 1).get(j).getColor()
                ) {
                    Main.LOG.debug("Vertical check true at ", boardFields.get(i).get(j));
                    return true;
                }

            }
        }



        /*
        /#Crosschecks
         */
        Main.LOG.debug("Cross check");
        for (int i = 1; i < boardFields.size() - 1; i++) {
            for (int j = 1; j < boardFields.get(i).size() - 1; j++) {
                if (
                        (!boardFields.get(i - 1).get(j - 1).isEmpty() &&
                                !boardFields.get(i).get(j).isEmpty() &&
                                !boardFields.get(i - 1).get(j - 1).isEmpty() &&

                                boardFields.get(i - 1).get(j - 1).getColor() ==
                                        boardFields.get(i).get(j).getColor() &&
                                boardFields.get(i + 1).get(j + 1).getColor() ==
                                        boardFields.get(i).get(j).getColor() &&
                                boardFields.get(i + 1).get(j + 1).getColor() ==
                                        boardFields.get(i - 1).get(j - 1).getColor())
                                ||
                                (!boardFields.get(i - 1).get(j + 1).isEmpty() &&
                                        !boardFields.get(i).get(j).isEmpty() &&
                                        !boardFields.get(i + 1).get(j - 1).isEmpty() &&
                                        boardFields.get(i - 1).get(j + 1).getColor() ==
                                                boardFields.get(i).get(j).getColor() &&
                                        boardFields.get(i + 1).get(j - 1).getColor() ==
                                                boardFields.get(i).get(j).getColor() &&
                                        boardFields.get(i - 1).get(j + 1).getColor() ==
                                                boardFields.get(i + 1).get(j - 1).getColor())) {
                    Main.LOG.debug("Cross check true at ", boardFields.get(i).get(j));
                    return true;
                }
            }
        }
        //@formatter:on
        return false;
    }
}
