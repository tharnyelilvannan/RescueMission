package ca.mcmaster.se2aa4.island.team01;

import java.util.ArrayList;

public class Location {
    ArrayList<Integer> location;

    Location(int x, int y) {
        location = new ArrayList<>();
        location.add(x);
        location.add(y);
    }
}
