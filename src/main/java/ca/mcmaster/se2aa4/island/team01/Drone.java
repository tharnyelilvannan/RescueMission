package ca.mcmaster.se2aa4.island.team01;
import ca.mcmaster.se2aa4.island.team01.Actions.Stop;

public class Drone {
    private GroundDetector groundDetector;
    private CreekFinder creekFinder;
    private FindIsland findIsland;
    // private FollowCoastline followCoastline;
    private Stop stop;
    private SearchIsland searchIsland;
    private boolean alignedWithCoastline = false;
    private boolean initalSearchDirectionSet = false;

    public Drone() {
        findIsland = new FindIsland();
        groundDetector = new GroundDetector();
        creekFinder = new CreekFinder();
        // followCoastline = new FollowCoastline();
        stop = new Stop();
        searchIsland = new SearchIsland();
    }
    public void updateInfo(ExtraInfo info) {
        if (info != null) {
            findIsland.updateGroundDetector(info);
            creekFinder.updateInfo(info);
            searchIsland.updateInfo(info);
            // followCoastline.updateInfo(info);  // Add this line
        }
    }
    
    public String initalizeExploration() {
        String decision;
        if (findIsland.isLandingPhase() == false) {
            decision = findIsland.searchForGround(); 
        } else if (creekFinder.isCreekFound() == false && findIsland.isLandingPhase() == true) {
            decision = searchIsland.exploreIsland();
        } else {
            decision = stop.returnToHeadquarters();
        }
        return decision;
    }
}
