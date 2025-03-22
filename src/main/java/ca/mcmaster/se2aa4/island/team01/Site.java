package ca.mcmaster.se2aa4.island.team01;

public class Site {
    private static Site instance = null;
    private static String id;
    private static Location coordinates;

    private Site() {
    }

    public static Site get() {
        if (instance == null) {
            instance = new Site();
        }

        return instance;
    }

    public void setID(String id) {
        this.id = id;
    }

    public void setLocation(Location coordinates) {
        this.coordinates = coordinates;
    }

}
