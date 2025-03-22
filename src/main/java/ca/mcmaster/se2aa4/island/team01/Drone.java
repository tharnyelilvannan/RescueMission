package ca.mcmaster.se2aa4.island.team01;
import ca.mcmaster.se2aa4.island.team01.Actions.Stop;

public class Drone {
    private GroundDetector groundDetector;
    private FindIsland findIsland;
    private Stop stop;
    private SearchIsland searchIsland;
    private boolean alignedWithCoastline = false;
    private boolean initalSearchDirectionSet = false;

    public Drone() {
        findIsland = new FindIsland();
        groundDetector = new GroundDetector();
        stop = new Stop();
        searchIsland = new SearchIsland();
    }
    public void updateInfo(ExtraInfo info) {
        if (info != null) {
            findIsland.updateGroundDetector(info);
            searchIsland.updateInfo(info);
        }
    }
    
    public String initalizeExploration() {
        String decision;
        if (findIsland.isLandingPhase() == false) {
            decision = findIsland.searchForGround(); 
        } else if (findIsland.isLandingPhase() == true) {
            decision = searchIsland.exploreIsland();
        } else {
            decision = stop.returnToHeadquarters();
        }
        return decision;
    }
}
