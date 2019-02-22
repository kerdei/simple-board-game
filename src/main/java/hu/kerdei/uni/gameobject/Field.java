package hu.kerdei.uni.gameobject;

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
}
