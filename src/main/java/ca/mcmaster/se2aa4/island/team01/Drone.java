package ca.mcmaster.se2aa4.island.team01;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import ca.mcmaster.se2aa4.island.team01.Actions.Stop;

/**
 * The Drone class handles the drone's flow of actions. It manages 
 * the exploration of the island by interacting with FindIsland, 
 * SearchIsland, IslandLength, and GroundDetector.
 */
public class Drone {
    private GroundDetector groundDetector;
    private FindIsland findIsland;
    private IslandLength islandLength;
    private Stop stop;
    private SearchIsland searchIsland;
    private boolean alignedWithCoastline = false;
    private boolean initalSearchDirectionSet = false;
    private final Logger logger = LogManager.getLogger();

    public Drone() {
        findIsland = new FindIsland();
        groundDetector = new GroundDetector();
        stop = new Stop();
        islandLength = new IslandLength();
        searchIsland = new SearchIsland();
    }

    /**
     * Updates exploration classes with JSON "extras" information.
     * @param info The response information from class action requests.
     */
    public void updateInfo(ExtraInfo info) {
        if (info != null) {
            findIsland.updateInfo(info);
            searchIsland.updateInfo(info);
            islandLength.updateInfo(info);
        }
    }

    /**
     * Initializes the exploration process based on the current state.
     * It chooses the appropriate action (e.g., exploring the island, measuring its length) 
     * or stopping based on boolean conditions.
     * @return A string request of the action to be taken.
     */
    public String initalizeExploration() {
        String decision;
        if (findIsland.isLandingPhase() == false) {
            decision = findIsland.explore(); 
        } else if (findIsland.isLandingPhase() == true && islandLength.hasFoundLength() == false) {
            decision = islandLength.explore();
        } else if (islandLength.hasFoundLength() == true) {
            int currentLength = islandLength.getIslandLength();
            searchIsland.updateIslandLength(currentLength);
            decision = searchIsland.explore();
        } else {
            decision = stop.returnToHeadquarters();
        }
        return decision;
    }
}
