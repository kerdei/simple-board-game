package hu.kerdei.uni.gameobject;

public class Player {

    String name;
    Color color;
    boolean isNext;

    public Player(String name, Color color, boolean isNext) {
        this.name = name;
        this.color = color;
        this.isNext = isNext;
    }

    public boolean isNext() {
        return isNext;
    }

    public void setNext(boolean next) {
        isNext = next;
    }

    public Color getColor() {
        return color;
    }
}
