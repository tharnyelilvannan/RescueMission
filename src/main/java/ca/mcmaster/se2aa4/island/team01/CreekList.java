package ca.mcmaster.se2aa4.island.team01;

import java.util.ArrayList;

public class CreekList {
    private ArrayList<Creek> creeks = new ArrayList<>();
    private static CreekList instance = null;

    private CreekList() {

    }

    public static CreekList get() {
        if (instance == null) {
            instance = new CreekList();
        }
        return instance;
    }

    public void addCreek(String id, int x, int y) {
        creeks.add(new Creek(id, x, y));
    }

    public ArrayList<Creek> getCreekList() {
        return creeks;
    }
}
