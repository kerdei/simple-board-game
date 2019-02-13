package hu.kerdei.uni.gameobject;

public enum Color {

    BLUE(0), RED(1);

    private int value;

    Color(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

}
