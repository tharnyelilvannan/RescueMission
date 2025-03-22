package ca.mcmaster.se2aa4.island.team01;

public class Site {
    private static Site instance = null;
    private String id;
    private Location coordinates;

    public Site(String id, Location coordinates) {
        this.id = id;
        this.coordinates = coordinates;
    }

}
