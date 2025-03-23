package ca.mcmaster.se2aa4.island.team01;

public class Creek {
    private String id;
    private int x;
    private int y;

    Creek(String id, int x, int y) {
        this.id = id;
        this.x = x;
        this.y = y;
    }

    public String getID() {
        return id;
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }
}
