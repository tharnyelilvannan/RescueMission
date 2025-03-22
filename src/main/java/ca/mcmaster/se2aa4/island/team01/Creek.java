package ca.mcmaster.se2aa4.island.team01;

public class Creek {
    private String id;
    private Location coordinates;

    Creek(String id, Location coordinates) {
        this.id = id;
        this.coordinates = coordinates;
    }

    public String getID() {
        return id;
    }

    public Location getLocation() {
        return coordinates;
    }
}
