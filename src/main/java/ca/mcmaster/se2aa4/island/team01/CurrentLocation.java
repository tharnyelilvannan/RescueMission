package ca.mcmaster.se2aa4.island.team01;

public class CurrentLocation extends Location {

    private static CurrentLocation instance = null; 

    private CurrentLocation() {
        super(1, 1);
    }

    public static CurrentLocation get() {
        if (instance == null) {
            instance = new CurrentLocation();
        }
        return instance;
    }

    public void changeXCoordinate(int x) {
        location.set(0, x);
    }

    public void changeYCoordinate(int y) {
        location.set(1, y);
    }

    public int getXCoordinate() {
        return location.get(0);
    }

    public int getYCoordinate() {
        return location.get(1);
    }
}
