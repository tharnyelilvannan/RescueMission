package ca.mcmaster.se2aa4.island.team01;

public class CurrentLocation extends Location implements Coordinates {

    private static CurrentLocation instance = null; 

    private CurrentLocation() {
        super(0, 0);
    }

    public static synchronized CurrentLocation get() {
        if (instance == null) {
            instance = new CurrentLocation();
        }
        return instance;
    }

    public void setXCoordinate(int x) {
        location.set(0, x);
    }

    public void setYCoordinate(int y) {
        location.set(1, y);
    }

    public int getXCoordinate() {
        return location.get(0);
    }

    public int getYCoordinate() {
        return location.get(1);
    }
}
