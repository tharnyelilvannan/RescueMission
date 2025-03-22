package ca.mcmaster.se2aa4.island.team01;

public class CurrentLocation extends Location {

    private static CurrentLocation instance = null; 

    private CurrentLocation() {
        super(0, 0);
    }

    public static CurrentLocation get() {
        if (instance == null) {
            instance = new CurrentLocation();
        }
        return instance;
        
    }
}
