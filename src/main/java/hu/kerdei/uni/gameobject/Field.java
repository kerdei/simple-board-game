package hu.kerdei.uni.gameobject;

import java.util.Objects;

public class Field {

    private Boolean isEmpty;
    private Color color;

    public Field(Boolean isEmpty, Color color) {
        this.isEmpty = isEmpty;
        this.color = color;
    }

    public Field(Color color) {
        this.color = color;
        isEmpty = false;
    }

    public Boolean isEmpty() {
        return isEmpty;
    }

    public Color getColor() {
        return color;
    }

    @Override
    public String toString() {
        return "Field{" +
                "isEmpty=" + isEmpty +
                ", color=" + color +
                ", obj=" + super.toString() +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Field field = (Field) o;
        return isEmpty.equals(field.isEmpty) &&
                color == field.color;
    }

    @Override
    public int hashCode() {
        int h = 0;
        final int prime = 31;
        h = prime * h + (isEmpty ? 1 : 2);
        h = prime * h + (color == Color.RED ? 3 : 4);
        return h;
    }
}
