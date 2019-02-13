package hu.kerdei.uni.gameobject;

import hu.kerdei.uni.Main;

import java.util.ArrayList;


public class Board {

    ArrayList<ArrayList<Field>> boardFields;

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

    public void switchFields(Pos fromPos, Pos toPos) {


        Field field1 = boardFields.get(fromPos.row).get(fromPos.column);
        Field field2 = boardFields.get(toPos.row).get(toPos.column);

        Main.LOG.debug(boardFields.get(toPos.row).get(toPos.column).toString());
        boardFields.get(toPos.row).set(toPos.column, field1);
        Main.LOG.debug(boardFields.get(toPos.row).get(toPos.column).toString());

        Main.LOG.debug(boardFields.get(fromPos.row).get(fromPos.column).toString());
        boardFields.get(fromPos.row).set(fromPos.column,field2);
        Main.LOG.debug(boardFields.get(fromPos.row).get(fromPos.column).toString());

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Board board = (Board) o;

        ArrayList<ArrayList<Field>> boardFieldsO = board.getBoardFields();
        if (boardFieldsO.size() != boardFields.size()) return false;
        else {
            for (int i = 0; i < boardFields.size(); i++) {

                ArrayList<Field> boardFieldsRowThis = boardFields.get(i);
                ArrayList<Field> boardFieldsRowO = boardFieldsO.get(i);

                for (int j = 0; j < boardFieldsRowThis.size(); j++) {

                    /*
                    If one of them empty but the other is not returns false
                     */

                    if (boardFieldsRowThis.get(j).isEmpty() && !boardFieldsRowO.get(j).isEmpty()
                            || !boardFieldsRowThis.get(j).isEmpty() && boardFieldsRowO.get(j).isEmpty()) {
                        return false;
                    }

                    /*
                    If fields are not empty but the colors doesn't match returns false
                     */

                    if (!boardFieldsRowThis.get(j).isEmpty() && !boardFieldsRowO.get(j).isEmpty() && (
                            boardFieldsRowThis.get(j).getColor().getValue() != boardFieldsRowO.get(j).getColor().getValue())) {
                        return false;
                    }
                }
            }

        }
        return true;
    }

    @Override
    public String toString() {

        StringBuilder stringBuilder = new StringBuilder();


        for (int i = 0; i < boardFields.size(); i++) {
            ArrayList<Field> row = boardFields.get(i);
            stringBuilder.append(i);
            stringBuilder.append(" ");
            for (int j = 0; j < row.size(); j++) {
                stringBuilder.append(row.get(j).getColor());
                stringBuilder.append(" ");

            }
        }
        return stringBuilder.toString();
    }
}
