package ca.mcmaster.se2aa4.island.team01;

import java.util.ArrayList;

/**
 * Singleton class that manages a list of creeks.
 * Provides methods to add creeks and retrieve the list of creeks.
 */
public class CreekList {
    private ArrayList<Creek> creeks = new ArrayList<>();
    private static CreekList instance = null;

    private CreekList() {
        // Private constructor to prevent instantiation
    }

    /**
     * Returns the singleton instance of CreekList.
     * @return The instance of CreekList.
     */
    public static synchronized CreekList get() {
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
