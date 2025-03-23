package ca.mcmaster.se2aa4.island.team01;

public class Creek implements Coordinates, ID {
    private String id;
    private int x;
    private int y;

    Creek(String id, int x, int y) {
        this.setID(id);
        this.setXCoordinate(x);
        this.setYCoordinate(y);
    }

    public void setXCoordinate(int x) {
        this.x = x;
    }

    public void setYCoordinate(int y) {
        this.y = y;
    }

    public String getID() {
        return id;
    }

    public void setID(String id) {
        this.id = id;
    }

    public int getXCoordinate() {
        return this.x;
    }

    public int getYCoordinate() {
        return this.y;
    }
}
