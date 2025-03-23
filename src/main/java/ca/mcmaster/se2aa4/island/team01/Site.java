package ca.mcmaster.se2aa4.island.team01;

public class Site {
    private static Site instance = null;
    private static String id;
    private static int x;
    private static int y;

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

    public void setLocation(int x, int y) {
        this.x = x;
        this.y = y;
    }

}
