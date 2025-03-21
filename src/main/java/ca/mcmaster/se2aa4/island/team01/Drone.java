package ca.mcmaster.se2aa4.island.team01;
import ca.mcmaster.se2aa4.island.team01.Actions.Stop;

public class Drone {
    private GroundDetector groundDetector;
    private CreekFinder creekFinder;
    private FindIsland findIsland;
    private FollowCoastline followCoastline;
    private Stop stop;

    public Drone() {
        findIsland = new FindIsland();
        groundDetector = new GroundDetector();
        creekFinder = new CreekFinder();
        followCoastline = new FollowCoastline();
        stop = new Stop();
        
    }
    public void updateInfo(ExtraInfo info) {
        if (info != null) {
            findIsland.updateGroundDetector(info);
            creekFinder.updateInfo(info);
            followCoastline.updateInfo(info);  // Add this line
        }
    }
    

    public String initalizeExploration() {
        String decision;
        if (findIsland.isLandingPhase() == false) {
            decision = findIsland.searchForGround(); 
        } else if (creekFinder.isCreekFound() == false && findIsland.isLandingPhase() == true) {
            decision = followCoastline.traverse();
        } else {
            decision = stop.returnToHeadquarters();
        }
        return decision;
    }
}
