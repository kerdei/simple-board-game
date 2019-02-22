package hu.kerdei.uni.gameobject;

public class Player {

    private String name;
    private Color color;
    private boolean isNext;

    public Player(String name, Color color, boolean isNext) {
        this.name = name;
        this.color = color;
        this.isNext = isNext;
    }

    public Player(Color color, boolean isNext) {
        this.color = color;
        this.isNext = isNext;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Color getColor() {
        return color;
    }

    public boolean isNext() {
        return isNext;
    }

    public void setNext(boolean next) {
        isNext = next;
    }
}
